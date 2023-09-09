import { create } from 'zustand'
import { devtools, persist } from 'zustand/middleware'
import darkModeConsumer from './darkMode'

export interface State {
    darkMode: boolean
    toggleDarkMode: () => void
}

const useStore = create<State>()(
    devtools(
        persist(
            set => ({
                darkMode: false,
                toggleDarkMode: function() {
                    set(state => ({ darkMode: !state.darkMode }))
                }
            }),
            {
                name: 'spel-debugger',
                onRehydrateStorage() {
                    return (state, error) => {
                        if (error) {
                            console.error('an error happened during hydration', error)
                        } else {
                            darkModeConsumer(state!)
                        }
                    }
                },
            }
        )
    )
)

useStore.subscribe(darkModeConsumer)

export default useStore
