import { State } from '.'

const body = document.querySelector('body') as HTMLBodyElement

const darkModeConsumer = (state: State) => {
    if (state.darkMode) {
        body.classList.add('dark')
    } else {
        body.classList.remove('dark')
    }
}

export default darkModeConsumer
