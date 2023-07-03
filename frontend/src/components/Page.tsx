import { faHeart } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { SiGithub, SiSpring } from '@icons-pack/react-simple-icons'
import { ReactNode } from 'react'
import Spel from './Spel'
import Container from './Container'
import useAppVersion from '../api/useAppVersion'

interface ShellProps {
    children: ReactNode
}

function Shell(props: ShellProps) {
    return (
        <div className="bg-gray-100 px-4 py-8">
            <Container>
                {props.children}
            </Container>
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
    const appVersion = useAppVersion()

    return (
        <Shell>
            <div className="flex flex-row items-center gap-8 py-12 text-gray-500">
                <img src="/icon-192.png" className="w-16" />
                <div>
                    <p className="font-medium text-md">
                        Debugger for Spring Expression Language (<Spel />) [v{appVersion.data}]
                    </p>
                    <p className="text-sm">
                        Made with <FontAwesomeIcon icon={faHeart} className="text-red-500" /> by <a className="underline" href="https://github.com/axelrindle">Axel Rindle</a>
                    </p>
                </div>

                <div className="flex-1"></div>
                
                <a
                    href="https://github.com/axelrindle/spel-debugger"
                    target="_blank"
                    rel="noreferrer"
                >
                    <SiGithub size={30} title="Application Source Code" />
                </a>
                <a
                    href="https://docs.spring.io/spring-framework/reference/core/expressions.html"
                    target="_blank"
                    rel="noreferrer"
                >
                    <SiSpring size={30} title="SpEL Reference Documentation" />
                </a>
            </div>
        </Shell>
    )
}