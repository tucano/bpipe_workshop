// see: https://code.google.com/p/bpipe/wiki/Directories
about title: "Bpipe fastqc with dir"

fastqc =
{
  // Start by telling Bpipe where the outputs are going to go
  output.dir = "qc_data"

  // Then tell Bpipe how the file name is getting transformed
  // Notice we don't need any reference to the output directory here!
  transform(".fastq.gz") to ("_fastqc.zip")
  {
    // Now in our command, Bpipe gives us the folder as the value,
    // while knowing that it is referencing the zip file output
    // specified in our transform
    println "FASTQC COMMAND EXAMPLE: fastqc -o $output.dir $input.gz"
    exec """
      mkdir -p $output.dir;
      touch $output;
    """
  }
}

run { "%.fastq.gz" * [fastqc] }
