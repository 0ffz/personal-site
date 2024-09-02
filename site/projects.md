## [Geary](https://github.com/MineInAbyss/Geary)

An entity component system I wrote from the ground up in Kotlin. It lets users split data into small components and query entities based on the data they hold. It uses archetypes as the primary approach to speed up queries, and the design would allow for data to be packed tightly in memory once value objects are added to Java. It also has entity relations built into the engine, inspired by [flecs](https://github.com/SanderMertens/flecs).

## Sysadmin work at [Mine in Abyss](https://mineinabyss.com/)

A community-driven recreation of Made in Abyss in Minecraft. I did game development and server infrastructure work across several projects that help us deploy and maintain servers as a small team:

- [Deployment Ansible playbook](https://github.com/MineInAbyss/ansible-in-abyss) that sets up a new server and services via docker compose. It uses traefik for ssl, as well as oauth2-proxy to force authentication via GitHub for our internal services.
- [Server config propagation playbook](https://github.com/MineInAbyss/server-config) that pulls and copies config updates from GitHub.
- [Keepup](https://github.com/MineInAbyss/Keepup) is a CLI tool I wrote to download server dependencies via [rclone](https://rclone.org/) as defined in a HOCON file. This format lets us define and reuse dependencies for many servers in one file.
- [Custom Docker images](https://github.com/MineInAbyss/Docker) that help tie all our tools into one package that's useful for production and local development.

<img alt="The golden bridge in Orth overlooks the Abyss" loading="lazy" class="full-bleed min-h-72 object-cover" src="/assets/images/orth.png"/>
<figcaption>The golden bridge in Orth overlooks the Abyss</figcaption>

## [Weekly task planner](https://github.com/0ffz/tasks)

A cross-platform task and week planning app written on Jetpack Compose. It uses sqlite for storage, with near-instant startup times on Android, alongside a quick-add feature.

<img alt="The application home screen on Desktop" loading="lazy"  src="/assets/images/tasks-desktop.png"/>
<figcaption>The application home screen on Desktop</figcaption>

## Game jams

I've worked in Unity and Godot with C# to create games for game jams. My latest entry was [Rock Bottom](https://github.com/0ffz/Ludum-Dare-46) in Ludum Dare 46. I worked in a team of three to create a physics puzzle game using an event driven architecture.

<img alt="Pet rocks in the pet rock shop return home" loading="lazy" src="/assets/images/rock_bottom.avif"/>
<figcaption>Pet rocks in the pet rock shop return home</figcaption>

## Frontend roundup

I've worked on a few projects involving more frontend work:

- [Clicky](https://github.com/0ffz/Clicky), an online vote room I made for a professor that wanted students to be able to answer impromptu questions in class. It's built on Django and the frontend is mostly pure HTML with skeleton CSS applied.
- [Sports venue manager](https://github.com/0ffz/CSCB07-final) was a school project that introduced me to Firebase and modern Android app architecture. I used a lot of newer Java-specific Android standards, rather than Kotlin, which ironically I've used for anything but Android.
- [*This* website](https://dvyy.me) is built using a mini static site generator I wrote in Kotlin. I can split things into reusable components without any client side JavaScript required. Learn more on its [project page](https://github.com/0ffz/personal-site).
