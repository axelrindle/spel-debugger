import { ReactNode } from 'react';

interface Props {
    children: ReactNode
}

export default function Container(props: Props) {
    return (
        <div className="container lg:max-w-screen-xl mx-auto">
            {props.children}
        </div>
    )
}
