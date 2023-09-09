import { Field, FieldAttributes } from 'formik';
import { DetailedHTMLProps, InputHTMLAttributes, ReactNode } from 'react';

interface FieldProps {
    label?: string | ReactNode
}

type InputAttributes = FieldAttributes<DetailedHTMLProps<InputHTMLAttributes<HTMLInputElement>, HTMLInputElement>>

type Props = FieldProps & InputAttributes

export function MyLabel(props: Props) {
    if (props.label === undefined) return null

    return (
        <label
            htmlFor={props.name}
            className="block font-medium text-gray-700 dark:text-gray-200"
        >
            {props.label}
        </label>
    )
}

export function FieldShell(props: Props) {
    return (
        <div>
            <MyLabel {...props} />
            {props.children}
        </div>
    )
}

export function MyFieldOnly(props: Props) {
    return (
        <Field
            {...props}
            className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm dark:bg-gray-700 dark:text-gray-200"
        />
    )
}

export function MyField(props: Props) {
    return (
        <FieldShell {...props}>
            <MyFieldOnly {...props} />
        </FieldShell>
    )
}
