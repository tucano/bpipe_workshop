about title: "Bpipe var and using: pretend mode"

REFERENCE_GENOME = "../../minify/genome/chr22.fa"

@Transform("bam")
align =
{
  var pretend : false

  doc title: "Align DNA reads with bwa mem",
      desc: "Use bwa mem to align reads on the reference genome.",
      constraints: "Input must be compressed (fastq.gz)",
      author: "davide.rambaldi@gmail.com"

  def command = """
    bwa mem $REFERENCE_GENOME $input1.fgz $input2.fgz |
      samtools view -bSu - |
      samtools sort - $output.bam.prefix;
  """

  if (pretend)
  {
    println """
      INPUTS: $inputs
      OUTPUT: $outputs
      COMMAND:
        $command
    """
    // overwrite command
    command = "touch $output"
  }

  exec command

}

run { "%_R*_%.fgz" * [align] }
