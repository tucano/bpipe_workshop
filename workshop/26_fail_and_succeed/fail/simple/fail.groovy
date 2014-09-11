about title: "Bpipe fail"

@Filter("mean_coverage")
mean_target_coverage =
{
  exec """
    awk -F "\t" '{print \$1"\t"\$23}' $input.txt > $output.txt
  """
}

verify_coverage =
{
  // We use groovy to check if we reach the coverage
  def lines = file(input).readLines()
  // remove header line
  lines.remove(0)
  def bad_samples = []
  lines.each { line ->
    def sample = line.split("\t")
    if (sample[1].toBigDecimal() < 30.0 )
    {
      bad_samples << sample[0]
    }
  }
  if (bad_samples.size > 0)
  {
    fail "SAMPLES ${bad_samples.join(",")} DID NOT REACH 30X COVERAGE"
  }

}


run { mean_target_coverage + verify_coverage }
