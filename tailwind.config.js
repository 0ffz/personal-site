/** @type {import('tailwindcss').Config} */
module.exports = {
    content: {
        files: [
            './src/main/kotlin/**/*.kt',
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
