about title: "YOUR PIPELINE TITLE GOES HERE"

FQZ_COMP         = "~/src/fqzcomp-4.6/fqz_comp"
REFERENCE_GENOME = "../../minify/genome/chr22.fa"
PICMERGE         = "java -jar ~/libexec/picard/MergeSamFiles.jar"

align =
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

merge =
{
  var validation_stringency : "SILENT"
  var create_index          : false
  var msd                   : true
  var assumed_sorted        : true
  var use_threading         : true

  doc title: "Merge bam files",
      desc: """
      <p>
        Use <b>MergeSamFiles</b> to merge bam files. Path of PICARD: $PICMERGE <br/>
        Merged with this options:<br/>
        <table>
          <tr><td>VALIDATION_STRINGENCY</td><td>$validation_stringency</td></tr>
          <tr><td>CREATE_INDEX</td><td>$create_index</td></tr>
          <tr><td>MSD</td><td>$msd</td></tr>
          <tr><td>ASSUME_SORTED</td><td>$assumed_sorted</td></tr>
          <tr><td>USE_THREADING</td><td>$use_threading</td></tr>
        </table>
      </p>
      """,
      author: "davide.rambaldi@gmail.com"

  def outputs = [ input.prefix.replaceAll(/_[0-9]{3}/,"") + '.bam' ]
  produce(outputs)
  {
    // PICARD need inputs as I=input1.bam I=input2.bam then:
    def input_string = inputs.collect() { bam -> "I=" + bam }.join(" ")
    exec """
      $PICMERGE $input_string
        O=$output
        VALIDATION_STRINGENCY=$validation_stringency
        CREATE_INDEX=$create_index
        MSD=$msd
        ASSUME_SORTED=$assumed_sorted
        USE_THREADING=$use_threading
    """
  }
}

run { "%_R*_%." * [align] + merge }
