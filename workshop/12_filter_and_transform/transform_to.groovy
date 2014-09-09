about title: "Bpipe transform to"

fastqc_transform =
{
  transform('.fastq.gz') to('_fastqc.zip')
  {
    println """
      INPUT:  $input
      OUTPUT: $output
      EXAMPLE COMMAND:
        fastqc -o . --noextract $input
    """
    exec "touch $output"
  }
}

run { fastqc_transform }
