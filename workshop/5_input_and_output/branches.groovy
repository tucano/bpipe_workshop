// 1. We slurp the json file passed as argument
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)

// 2. We define some stages
prepare =
{
  // keep the sample name as a branch variable, see next sessions
  branch.sample = branch.name

  println """
    BRANCH: ${branch.name}
    SAMPLE: ${branch.sample}
  """

  // create a scratch dir with exec if doesn't exists
  exec "mkdir -p ${branch.sample}"
}

@Transform("bam")
align =
{
  // use the branch.sample coming from previous stage as output.dir, see next sessions
  output.dir = branch.sample

  println """
    INPUTS: $inputs
    OUTPUT: $output
  """

  // we will see later a real alignment, for now we just create an empty file to make bpipe happy
  exec """
    echo "BAM FILE CONTENT" > $output.bam
  """
}

// 3. We run the pipeline using "branches" as input
run {
  branches * [
    prepare + "%_R*_%.fgz" * [align]
  ]
}
