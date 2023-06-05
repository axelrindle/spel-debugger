export interface SpelRequest {
    spel: string
    context: Record<string, any>
}

export interface SpelResponse {
    result: any | null
    error: string | null
}
