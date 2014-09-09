about title: "Bpipe produce with explicit outputs var"

/*
 * A common issue is that Bpipe's built in logic for transform and filter don't match what you want or expect for how the file names should come out.
 * For example, some tools don't let you specify the output files and just make up their own based on some built in logic. Other times you have a mapping
 * from input file to output file that is more complicated than a simple one to one (or one to many) that Bpipe allows for. And sometimes it is just a
 * matter of personal preference for how the file names should look.
 * In all these cases you can use the fact that Bpipe is built on top of Groovy to write a tiny piece of logic that says how files
 * should be transformed. You can then supply your calculated outputs explicitly to a produce statement to tell Bpipe exactly what you expect to be "produced".
*/

REFERENCE_GENOME = "../../minify/genome/chr22.fa"

align =
{
  // We explict the outputs as a List
  // Note the use of file bpipe keyword (https://code.google.com/p/bpipe/wiki/File)
  // We remove R1 and R2
  def outputs = [ file("${input1.fgz}").name.replaceAll(/_R[12]/,"").prefix + '.bam' ]

  println "OUTPUTS: $outputs"

  produce(outputs)
  {
    exec """
      bwa mem $REFERENCE_GENOME $input1.fgz $input2.fgz |
      samtools view -bSu - |
      samtools sort - $output.bam.prefix
    """
  }
}

run { align }
