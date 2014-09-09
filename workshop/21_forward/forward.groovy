about title : "Forward example"

PICMERGE = "java -jar ~/libexec/picard/MergeSamFiles.jar"

index_bam =
{
  transform('bam.bai')
  {
    exec "samtools index $input.bam"
  }
  forward input.bam
}

merge_bam =
{
  filter("merge")
  {
    def input_strings = inputs.collect() { return "I=" + it}.join(" ")
    exec """
      $PICMERGE $input_strings
        O=$output
        VALIDATION_STRINGENCY=SILENT
        CREATE_INDEX=false
        MSD=true
        ASSUME_SORTED=true
        USE_THREADING=true
    """
  }
}

run { "%.bam" * [index_bam] + merge_bam }
