export interface ContextVariable {
    id: string
    key: string
    value: unknown
}

export interface SpelRequest {
    spel: string
    context: ContextVariable[]
}

export interface SpelResponse {
    result: any | null
    error: string | null
}
