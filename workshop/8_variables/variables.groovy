about title: "Variables in groovy"

// Explicit variables are ones you define yourself
REFERENCE_GENOME="hg19"

// RUN WITH: bpipe run variables.groovy example.*
explore_inputs =
{
  // Implicit variables are special variables
  // that are made available to your Bpipe pipeline stages automatically.
  println "YOUR INPUTS ARE STORED IN THE inputs VARIABLE $inputs"
  println ""
  println "SIZE: WE HAVE ${inputs.size()} INPUTS"
  println ""
  for(i in inputs)
  {
    println "LOOPING IN INPUTS: $i"
  }
  println ""
  println "WE CAN ACCESS THE INPUTS ALSO WITH:"
  println "INPUT1 : $input1"
  println "INPUT2 : $input2"
  println "INPUT3 : $input3"
  println "INPUT4 : $input4"
  println "INPUT5 : $input5"
  println "INPUT6 : $input6"
  println ""
  println "INPUTS Object is of class: ${inputs.class}, but we can access to it as a List where first element is ${input[0]}"
  println ""

  // EXPLICIT VARIABLES
  println "The Explicit variables are shared between braches/stages: REFERENCE_GENOME IS:$REFERENCE_GENOME"

  // EXTENSION SYNTAX
  println "THE FIRST BAM FILE IS: ${input.bam}"
  println "THE FIRST GZ FILE IS: ${input.gz}"
  println "ALL GZ FILES: ${inputs.gz}"

  // PREFIX
  println "The PREFIX of $input1 is ${input1.prefix}"
  println "The PREFIX of ${input1.gz} is ${input1.gz.prefix}"

  // We can prefix any String
  def manual_prefix = "test.bam".prefix
  println "We can prefix any String: the prefix of test.bam is $manual_prefix"

}

variables_evaluation =
{
  // Double quotes cause variables to be expanded before they are passed to the shell.
  exec "echo INPUT FILE IS $input"
  // you could embed single quotes around the file name
  exec "echo INPUT FILE WITH EXTENSION IS '$input.bam'"
  // Triple quotes are useful because they accept embedded newlines.
  exec """
    echo ${input.bam}
  """
}

bash_variables =
{
  // Bpipe always expects a $ sign to be followed by a variable name.
  // Thus operations in Bash that use $ for other things need to have
  // the $ escaped so that Bpipe does not interpret it.
  exec "for i in \$(ls *.fastq.gz); do file $i; done"
}

run { explore_inputs + variables_evaluation + bash_variables }
