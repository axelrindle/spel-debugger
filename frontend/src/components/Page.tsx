import { ReactNode } from 'react'
import Spel from './Spel'

interface ShellProps {
    children: ReactNode
}

function Shell(props: ShellProps) {
    return (
        <div className="bg-gray-100 py-8">
            <div className="max-w-screen-xl mx-auto">
                {props.children}
            </div>
        </div>
    )
}

export function Header() {
    return (
        <Shell>
            <p className="text-4xl text-lime-600 font-bold">
                Debugger for Spring Expression Language (<Spel />)
            </p>
        </Shell>
    )
}

export function Footer() {
    return (
        <Shell>
            Footer
        </Shell>
    )
}