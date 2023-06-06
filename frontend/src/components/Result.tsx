import { UseSpelResult } from '../api/useSpel';

interface Props {
    mutation: UseSpelResult
}

export default function Result({ mutation }: Props) {
    if (mutation.isLoading) {
        return 'Loading...'
    }

    if (mutation.isError) {
        return 'Error: ' + mutation.error.message
    }

    return mutation.data?.result ?? 'No data.'
}