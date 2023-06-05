import { FormEventHandler } from 'react'
import useSpel from './api/useSpel'
import Navbar from './components/Navbar'
import Result from './components/Result'

export default function App() {
  const mutation = useSpel()
  const onSubmit: FormEventHandler<HTMLFormElement> = event => {
    event.preventDefault()
    mutation.mutate(new FormData(event.target as HTMLFormElement))
  }

  return (<>
    <Navbar />
    <div className="max-w-screen-xl mx-auto py-8 prose">
      <p className="text-4xl">
        Spring Expression Language (SpEL) Debugger
      </p>
      <p>
        Input a SpEL expression below and the output will be shown next to it.
      </p>
      <p>
        You may also feed the expression with context variables.
      </p>

      <form onSubmit={onSubmit}>
        <div>
          <label htmlFor="spel" className="block font-medium text-gray-700">
            SpEL Expression
          </label>

          <input
            type="text"
            id="spel"
            name="spel"
            placeholder="${my.variable}"
            defaultValue="#{T(java.time.LocalDate).parse('${property.name}')}"
            className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
          />
        </div>

        <div className="flex justify-center mt-8">
          <button
            className="group relative inline-block overflow-hidden border border-indigo-600 px-16 py-3 focus:outline-none focus:ring"
            type="submit"
          >
            <span
              className="absolute inset-y-0 left-0 w-[2px] bg-indigo-600 transition-all group-hover:w-full group-active:bg-indigo-500"
            ></span>

            <span
              className="relative text-sm font-medium text-indigo-600 transition-colors group-hover:text-white"
            >
              Submit
            </span>
          </button>
        </div>
      </form>

      <hr />

      <div>
        <label htmlFor="result" className="font-medium text-gray-700">
          Result Output
        </label>
        <pre id="result" className="mt-1">
          <code>
            <Result mutation={mutation} />
          </code>
        </pre>
      </div>
    </div>
  </>)
}
