about title: "Mark Duplicates"

MARKDUPLICATES = "java -Djava.io.tmpdir=/tmp -jar ~/libexec/picard/MarkDuplicates.jar"

@Filter("dedup")
mark_duplicates =
{
  doc title: "Mark Duplicates in BAM files",
      author: "davide.rambaldi@gmail.com"

  exec """
    $MARKDUPLICATES I=$input.bam O=$output.bam
    VALIDATION_STRINGENCY=SILENT
    CREATE_INDEX=true
    REMOVE_DUPLICATES=false
    ASSUME_SORTED=true
    METRICS_FILE=sample.metrics
  """
}

run { mark_duplicates }
