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

// Once you define your pipeline stages
// you can build and run your pipeline
// by joining the stages using the plus operator:
run {  hello + world }
