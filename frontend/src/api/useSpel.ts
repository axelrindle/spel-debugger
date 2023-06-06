import { UseMutationResult, useMutation } from 'react-query';
import { SpelRequest, SpelResponse } from '../types';

export type UseSpelResult = UseMutationResult<SpelResponse, SpelResponse, FormData>

function getUrl(): string|URL {
    if (import.meta.env.PROD) {
        return '/spel';
    }

    return new URL('/spel', import.meta.env.VITE_API_URL)
}

export default function useSpel() {
    return useMutation<SpelResponse, SpelResponse, FormData>(async (data) => {
        const response = await fetch(getUrl(), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                spel: data.get('spel'),
                context: {
                    'property.name': '2023-05-06'
                },
            } as SpelRequest),
        })

        return await response.json() as SpelResponse
    })
}