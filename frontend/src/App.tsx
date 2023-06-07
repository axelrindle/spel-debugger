import { faExclamationTriangle, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { FieldArray, Form, Formik } from 'formik'
import { nanoid } from 'nanoid'
import { useState } from 'react'
import useSpel from './api/useSpel'
import { MyField, MyFieldOnly } from './components/Form'
import { Footer, Header } from './components/Page'
import Result from './components/Result'
import Spel from './components/Spel'
import { ContextVariable, SpelRequest } from './types'

const formInitialValues: SpelRequest = {
  spel: "#{T(java.time.LocalDate).parse('${property.name}')}",
  context: [
    {
      id: nanoid(),
      key: 'property.name',
      value: '2023-05-06'
    }
  ]
}

export default function App() {
  const [contextToDelete, setContextToDelete] = useState<string>()

  const mutation = useSpel()

  return (<>
    <Header />

    <div className="flex-1">
      <div className="max-w-screen-xl mx-auto py-8 prose">
        <p>
          Input a <Spel /> expression below and the output will be shown below.
        </p>
        <p>
          You may also feed the expression with context variables.
        </p>

        <Formik
          initialValues={formInitialValues}
          onSubmit={async (data) => {
            await mutation.mutateAsync(data)
          }}
        >
          {({ values }) => (
            <Form className="flex flex-col gap-8">
              <MyField
                type="text"
                name="spel"
                label={<span><Spel/> Expression</span>}
              />

              <div>
                <FieldArray name="context">
                  {({ remove, push }) => (<>
                    <MyField
                      type="hidden"
                      name="context_label"
                      label={<>
                        <span>Context Variables</span>
                        <button
                          type="button"
                          className="text-green-600 ml-2"
                          title="Add new variable"
                          onClick={() => push({ id: nanoid(), key: '', value: '' } as ContextVariable) }
                        >
                          <FontAwesomeIcon icon={faPlus} />
                        </button>
                        {/* <button
                          type="button"
                          className="ml-2"
                          title="Delete all variables"
                          onClick={() => {
                            if (values.context.length === 0) return
                            
                            if (contextToDelete === 'all') {
                              const length = values.context.length
                              for (let index = 0; index < length; index++) {
                                remove(index)
                              }
                              setContextToDelete(undefined)
                            } else {
                              setContextToDelete('all')
                            }
                          }}
                        >
                          <FontAwesomeIcon
                            className={contextToDelete === 'all' ? 'text-red-600' : 'text-orange-600'}
                            icon={contextToDelete === 'all' ? faExclamationTriangle : faBroom}
                          />
                        </button> */}
                      </>}
                    />

                    {values.context.length > 0 ? null : <p className="italic">No variables...</p>}
                    {values.context.map((entry, index) => (
                      <div className="flex flex-row gap-4" key={index}>
                        <MyFieldOnly
                          type="text"
                          placeholder="key"
                          name={`context.${index}.key`}
                        />
                        <MyFieldOnly
                          type="text"
                          placeholder="value"
                          name={`context.${index}.value`}
                        />
                        <button
                          type="button"
                          tabIndex={-1}
                          className={contextToDelete === entry.id ? 'text-red-600' : 'text-orange-600'}
                          title={contextToDelete === entry.id ? 'Click to confirm' : 'Click to delete'}
                          onClick={() => {
                            if (values.context.length === 0) return

                            if (contextToDelete === entry.id) {
                              remove(index)
                              setContextToDelete(undefined)
                            } else {
                              setContextToDelete(entry.id)
                            }
                          }}
                        >
                          <FontAwesomeIcon
                            className="w-6 text-lg"
                            icon={contextToDelete === entry.id ? faExclamationTriangle : faTrash}
                          />
                        </button>
                      </div>
                    ))}
                  </>)}
                </FieldArray>
              </div>

              <div className="flex justify-center mt-8">
                <button
                  className="group relative inline-block overflow-hidden border border-green-600 px-16 py-3 focus:outline-none focus:ring"
                  type="submit"
                >
                  <span
                    className="absolute inset-y-0 left-0 w-[2px] bg-green-600 transition-all group-hover:w-full group-active:bg-green-500"
                  ></span>

                  <span
                    className="relative text-sm font-medium text-green-600 transition-colors group-hover:text-white"
                  >
                    Submit
                  </span>
                </button>
              </div>
            </Form>
          )}
        </Formik>

        <hr />

        <Result mutation={mutation} />
      </div>
    </div>

    <Footer />
  </>)
}
