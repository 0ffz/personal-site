/** @type {import('tailwindcss').Config} */
module.exports = {
    content: {
        files: [
            './src/**/*.kt',
            './site/**/*.md',
        ]
    },
    fontFamily: {
        'mono': ['"JetBrains Mono"', 'monospace'],
    },
    theme: {
        extend: {},
    },
    plugins: [
        require('@tailwindcss/typography'),
    ],
}
