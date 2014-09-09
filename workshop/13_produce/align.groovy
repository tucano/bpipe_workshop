about title: "Bpipe handle different kind of compressions in the same stage"

FQZ_COMP = "~/src/fqzcomp-4.6/fqz_comp"
REFERENCE_GENOME = "../../minify/genome/chr22.fa"

align =
{
  // Get extension
  def input_extension = ""
  def r1 = ""
  def r2 = ""

  println "INPUTS: $inputs"

  if ( inputs.every { it.endsWith(".gz") } )
  {
    println "INPUT IS GUNZIPPED"
    input_extension = '.fastq.gz'
    r1 = "<( gzcat $input1 )"
    r2 = "<( gzcat $input2 )"
  }
  else if ( inputs.every { it.endsWith(".fgz") } )
  {
    println "INPUT IS GUNZIPPED"
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

  def outputs = [ file(input1).name - input_extension + '.bam']

  produce(outputs)
  {
    exec "bwa mem $REFERENCE_GENOME $r1 $r2  | samtools view -bSu - | samtools sort - $output.bam.prefix"
  }
}


run { "%_R*_%." * [align] }
