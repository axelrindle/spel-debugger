import { useMutation } from '@tanstack/react-query';
import { SpelRequest, SpelResponse } from '../types';

interface Context {
    start: Date
    duration?: number
}

function getUrl(): string|URL {
    if (import.meta.env.PROD) {
        return '/spel';
    }

    return new URL('/spel', import.meta.env.VITE_API_URL)
}

export default function useSpel() {
    return useMutation<SpelResponse, Error, SpelRequest, Context>({
        mutationFn: async (data) => {
            const dataTransformed = {
                ...data,
                context: data.context.reduce((last, current) => ({ ...last, [current.key]: current.value}), {})
            }
            const response = await fetch(getUrl(), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dataTransformed),
            })
    
            const result = await response.json() as SpelResponse
    
            if (!response.ok) {
                throw new Error(result.error ?? 'Unknown error!')
            }
    
            return result
        },
        onMutate() {
            return { start: new Date() }
        },
        onSettled: (_response, _error, _request, context) => {
            if (context === undefined) return
            context.duration = new Date().getTime() - context.start.getTime()
        },
    })
}

export type UseSpelResult = ReturnType<typeof useSpel>
