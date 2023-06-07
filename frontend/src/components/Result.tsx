import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { UseSpelResult } from '../api/useSpel';
import { FieldShell } from './Form';
import { faCheck, faClose, faPause, faSpinner } from '@fortawesome/free-solid-svg-icons';

interface Props {
    mutation: UseSpelResult
}

function ResultText({mutation}: Props) {
    if (mutation.isLoading) {
        return 'Loading...'
    }

    if (mutation.isError) {
        return mutation.error.message
    }

    return mutation.data?.result ?? 'No data.'
}

export default function Result({ mutation }: Props) {
    return (
        <FieldShell
            name="result"
            label={
                <div>
                    <span className="mr-2">Result</span>
                    {mutation.isError && <FontAwesomeIcon icon={faClose} className="text-red-600" />}
                    {mutation.isSuccess && <FontAwesomeIcon icon={faCheck} className="text-green-600" />}
                    {mutation.isLoading && <FontAwesomeIcon icon={faSpinner} className="text-sky-600" spin />}
                    {mutation.isIdle && <FontAwesomeIcon icon={faPause} className="text-sky-600" />}
                </div>
            }
        >
            <pre id="result" className="mt-1">
                <code>
                    <ResultText mutation={mutation} />
                </code>
            </pre>
            <p>
                Type: <u>{mutation.data?.type}</u>
            </p>
            <p>
                Duration: <u>{mutation.context?.duration} ms</u>
            </p>
        </FieldShell>
    )
}