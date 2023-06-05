import { UseMutationResult, useMutation } from 'react-query';
import { SpelRequest, SpelResponse } from '../types';

export type UseSpelResult = UseMutationResult<SpelResponse, SpelResponse, FormData>

export default function useSpel() {
    return useMutation<SpelResponse, SpelResponse, FormData>(async (data) => {
        const url = new URL('/spel', import.meta.env.VITE_API_URL)
        const response = await fetch(url, {
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