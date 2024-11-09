---
title: How I manage more than dotfies with chezmoi
desc: Exploring chezmoi's templating and scripting functionality to help automate machine setup.
date: 2024-11-09
year: 2024
template: blog
tags: [ provisioning ]
---

I've been using [chezmoi](https://www.chezmoi.io/) for a while now to help me keep my laptop and desktop in sync. In short, it's a small program that can copy over files from a git repo to any machines you use. It includes a templating system with some built in tools like reading from bitwarden, as well as the ability to [run scripts](https://www.chezmoi.io/user-guide/use-scripts-to-perform-actions/#run-a-script-when-the-contents-of-another-file-changes) when tracked files change.

[The docs](https://www.chezmoi.io/) are quite nice and contain a lot of examples too, so I'll mostly highlight useful things I use on my own system. I'll try to keep this page updated as I come across more in the future!

# Templates

### Syncing ssh keys

I keep my public and private ssh keys in bitwarden under notes, then load one under `id_rsa.tmpl`, as well as `authorized_keys.tmpl` for the public variant:

```bash
{{ (bitwarden "item" "ssh_private_key").notes }}
```

You can prefix files with `create_` in chezmoi to ensure they only run once, if the file doesn't already exist on your machine, avoiding a bitwarden login every time.

Another quick tip, you can set up an ssh config file in `~/.ssh/config` for commonly used machines, then just `ssh myhost`:

```bash
Host myhost
    HostName my.host.com
    User me
    Port 42
```

# Scripts

By prefixing a script in the chezmoi root or inside `.chezmoiscripts` with `run_onchange_`, chezmoi can execute it whenever it detects the file has changed.
Combining this with templates, we can generate a hash for any other file in our repo and run scripts whenever it changes. We can also use `after_` with this, to run changes after chezmoi has copied files to their correct places.
Here's the example shown in the docs:

**run_onchange_dconf-load.sh.tmpl**

```bash
#!/bin/bash

# dconf.ini hash: {{ include "dconf.ini" | sha256sum }}
dconf load / < {{ joinPath .chezmoi.sourceDir "dconf.ini" | quote }}
```

## Managing Docker services

I use docker compose to run services locally, namely syncthing, which needs to run across all my machines.
So, let's store the `docker-compose.yml` in chezmoi and set it up to deploy the stack whenever the file changes:

**run_onchange_after_start-docker-compose.sh.tmpl**

```bash
#!/bin/bash

# hash: {{ include "dot_config/services/docker-compose.yml" | sha256sum }}
docker compose -f ~/.config/services/docker-compose.yml up -d
```

## Automating package installations

I recently installed Nix on my Fedora Silverblue system to help me install cli tools and such without having to go through ostree.
I use home-manager to set up the packages I want, let's track that in too!

**run_onchange_after_home-manager-reload.sh.tmpl**

```bash
#!/bin/bash

# hash: {{ include "dot_config/home-manager/home.nix" | sha256sum }}
home-manager switch
```

## Automating distrobox configurations

Distrobox is a tool for developers to work inside containers that are more integrated with the host system, think of it sort of like WSL on Windows.
On Fedora Atomic desktops, I've found it useful to have a main container to install development packages via dnf, however with Nix on my system, I'd love to automatically have access to the cli tools I install on my host inside this environment.

To accomplish this, I create a `distrobox.ini` definition under `~/.config/distrobox/`:

```ini
[system]
image=registry.fedoraproject.org/fedora-toolbox:latest
volume="/nix:/nix"
```

And the automation:

**run_onchange_after_update-distrobox.sh.tmpl**

```bash
#!/bin/bash

# hash: {{ include "dot_config/distrobox/distrobox.ini" | sha256sum }}
distrobox assemble create --replace --file ~/.config/distrobox/distrobox.ini
```

# Running machine setup tasks

I quite like the dconf example given by the docs. With a bit of care, we can write idempotent scripts for simple things where tools like Ansible might add a lot of mental and runtime overhead. So here's some scripts I came up with.

## Setting up Nix on my machines

This one is quite simple, every time we run chezmoi it'll check if the `/nix` directory exists, if not it'll install it and home-manager based on this [GitHub gist](https://gist.github.com/queeup/1666bc0a5558464817494037d612f094). (Sometime I'll probably split these into two separate steps in case nix installs but home-manager fails.)

**run_ensure-nix-installed.sh**

```bash
#!/bin/bash

# Check if /nix directory exists
if [ -d "/nix" ]
then
    exit
fi

# Source: https://gist.github.com/queeup/1666bc0a5558464817494037d612f094
# Install nix on Fedora silverblue
curl --proto '=https' --tlsv1.2 -sSf -L https://install.determinate.systems/nix | \
    sh -s -- install ostree --no-confirm --persistence=/var/lib/nix

# Fix sudo
echo "Defaults  secure_path = /nix/var/nix/profiles/default/bin:/nix/var/nix/profiles/default/sbin:$(sudo printenv PATH)" | sudo tee /etc/sudoers.d/nix-sudo-en

# Source nix
. /nix/var/nix/profiles/default/etc/profile.d/nix-daemon.sh

# Setup home-manager https://julianhofer.eu/blog/01-silverblue-nix/
nix-channel --add https://nixos.org/channels/nixpkgs-unstable
nix-channel --add https://github.com/nix-community/home-manager/archive/master.tar.gz home-manager
nix-channel --update
nix-shell '<home-manager>' -A install
```
