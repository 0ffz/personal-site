/** @type {import('tailwindcss').Config} */
module.exports = {
    content: {
        files: [
            './src/**/*.kt',
            './site/**/*.md',
        ]
    },
    theme: {
        fontFamily: {
            'mono': ['JetBrains Mono', 'monospace'],
        },
        extend: {},
    },
    plugins: [
        require('@tailwindcss/typography'),
    ],
}
