import { useQuery } from '@tanstack/react-query';
import getUrl from './url';

interface ActuatorResponse {
    build: {
        version: string
    }
}

export default function useAppVersion() {
    return useQuery({
        queryKey: ['app-version'],
        refetchOnWindowFocus: false,
        queryFn: async () => {
            const response = await fetch(getUrl('actuator/info'))
            return await response.json() as ActuatorResponse
        }
    })
}
