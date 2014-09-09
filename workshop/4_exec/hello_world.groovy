/**************************************************************************
 * A minimal pipeline is composed by:
 * 1. one or more stages
 * 2. A "run" closure (the pipeline)
 *
 **************************************************************************/

hello = {
  // An exec with single quotes
  exec "echo 'Hello'"
}

world = {
  // An exec with triple quotes
  exec """
    echo 'World'
  """
}

run {  hello + world }
