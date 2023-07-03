import { useQuery } from '@tanstack/react-query';
import getUrl from './url';

export default function useAppVersion() {
    return useQuery({
        queryKey: ['app-version'],
        queryFn: async () => {
            const response = await fetch(getUrl('version'))
            return await response.text()
        }
    })
}
