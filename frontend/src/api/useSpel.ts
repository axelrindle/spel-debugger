import { useMutation } from '@tanstack/react-query';
import { SpelResponse } from '../types';

function getUrl(): string|URL {
    if (import.meta.env.PROD) {
        return '/spel';
    }

    return new URL('/spel', import.meta.env.VITE_API_URL)
}

export default function useSpel() {
    return useMutation<SpelResponse, Error, unknown>(async (data) => {
        const response = await fetch(getUrl(), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })

        const result = await response.json() as SpelResponse

        if (!response.ok) {
            throw new Error(result.error ?? 'Unknown error!')
        }

        return result
    })
}

export type UseSpelResult = ReturnType<typeof useSpel>
