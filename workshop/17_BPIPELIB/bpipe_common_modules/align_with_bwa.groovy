align_with_bwa =
{
  // get canonical path for the reference genome
  def genome_path = file(REFERENCE_GENOME).name

  doc title: "Align paired reads with bwa mem",
      desc: """
        Use <b>bwa mem</b> to align reads on the reference genome located at this path: $genome_path.
        <br/>
        Can handle fastq, fastq.gz and fqz files. For fqz files uses $FQZ_COMP to decompress.
        <br/>
        We use mem instead of aln + sampe. We remove R[12] from file name in this stage.
      """,
      constraints: "Assume paired reads, on MACOSX there is a bug in zcat, USE gzcat!",
      author: "davide.rambaldi@gmail.com"

  // Get extension
  def input_extension = ""
  def r1 = ""
  def r2 = ""

  println "INPUTS: $inputs"

  if ( inputs.every { it.endsWith(".gz") } )
  {
    println "INPUT IS GUNZIPPED (fastq.gz)"
    input_extension = '.fastq.gz'
    r1 = "<( gzcat $input1 )"
    r2 = "<( gzcat $input2 )"
  }
  else if ( inputs.every { it.endsWith(".fgz") } )
  {
    println "INPUT IS GUNZIPPED (fgz)"
    input_extension = '.fgz'
    r1 = "<( gzcat $input1 )"
    r2 = "<( gzcat $input2 )"
  }
  else if ( inputs.every { it.endsWith(".fqz") } )
  {
    println "INPUT IS FQZ COMPRESSED"
    input_extension = '.fqz'
    r1 = "<( $FQZ_COMP -d $input1 )"
    r2 = "<( $FQZ_COMP -d $input2 )"
  }
  else if ( inputs.every { it.endsWith(".fastq") } )
  {
    println "INPUT IS UNCOMPRESSED"
    input_extension = '.fastq'
    r1 = "$input1"
    r2 = "$input2"
  }
  else
  {
    fail "Unrecognized input type, input files have all the same extension type?"
  }

  def outputs = [ file(input1).name.replaceAll(/_R[12]/,"") - input_extension + '.bam']

  produce(outputs)
  {
    exec "bwa mem $REFERENCE_GENOME $r1 $r2  | samtools view -bSu - | samtools sort - $output.bam.prefix"
  }
}
