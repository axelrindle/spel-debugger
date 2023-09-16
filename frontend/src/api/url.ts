export default function getUrl(relative: string): URL {
    if (import.meta.env.PROD) {
        return new URL('/' + relative);
    }

    return new URL('/' + relative, import.meta.env.VITE_API_URL)
}
