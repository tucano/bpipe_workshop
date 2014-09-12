split_by_chromosome =
{
  doc title: "Split BAM files by chromosome",
      author: "davide.rambaldi@gmail.com"

  // Same as @Filter, but using a BLOCK CODE
  // Useful if you have branch/stage variables
  filter("$chr")
  {
    exec "samtools view $input.bam $chr > $output"
  }
}

run {
  chr(1..22,'X','Y') * [split_by_chromosome]
}
