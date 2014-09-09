split_by_chromosome =
{
  doc title: "Split BAM files by chromosome",
      author: "davide.rambaldi@gmail.com"

  def output_file = input.prefix + '.' + chr + '.bam'
  produce("$output_file") {
    exec "samtools view $input.bam $chr > $output_file"
  }
}


// HERE WE RUN THE PIPELINE
run {
  chr(1..22,'X','Y') * [split_by_chromosome]
}
