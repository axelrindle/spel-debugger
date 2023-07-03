export default function getUrl(relative: string): string|URL {
    if (import.meta.env.PROD) {
        return '/' + relative;
    }

    return new URL('/' + relative, import.meta.env.VITE_API_URL)
}
