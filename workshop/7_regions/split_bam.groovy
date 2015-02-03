split_by_chromosome =
{
  doc title: "Split BAM files by chromosome",
      author: "davide.rambaldi@gmail.com"

  // Same as @Filter, but using a BLOCK CODE
  // Useful if you have branch/stage variables

  transform("sam")
  {
    output.dir = "${input.replaceAll(/\/.*/,"")}_output"
    exec "samtools view $input.bam $chr > $output"
  }
}

run {
  chr(1..22,'X','Y','M') * [split_by_chromosome]
}
