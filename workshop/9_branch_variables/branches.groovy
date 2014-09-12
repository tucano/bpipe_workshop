about title: "Bpipe branches"

@Transform("bam")
align = {
  // This is a LOCAL variable that have THIS STAGE SCOPE
  def local_var = "test"
  // In opposite branch variables propagate on all subsequent stages of this branch
  // THE BRANCH NAME DEPENDS ON THE REGEXP USED
  // ITS COLLECTD the % parts: sample1_L001.004
  println "BRANCH NAME: ${branch.name}"
  println "INPUTS: $inputs"
  // I can store variables in the brach data structure
  branch.sample = branch.name.replaceAll(/_.*/,"")
  branch.casava_group  = branch.name.replaceAll(/.*\./,"")

  println """
    BRANCH VARS:
      SAMPLE       : ${branch.sample}
      CASAVA GROUP : ${branch.casava_group}
  """
  exec """
    cat $input1 $input2 > $output
  """
}

check_align =
{
  println "INPUTS: $inputs"
  println "BRANCH NAME: ${branch.name}"
  println """
    BRANCH VARS:
      SAMPLE       : ${branch.sample}
      CASAVA GROUP : ${branch.casava_group}
  """
  // I can use this info to rename the file for example
  def new_output = "${branch.sample}_${branch.casava_group}.bam"

  produce(new_output)
  {
    exec "cp $input $new_output"
  }
}

run { "%_R*_%.fgz" * [align + check_align] }
