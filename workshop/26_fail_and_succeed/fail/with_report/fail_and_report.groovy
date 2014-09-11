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
  // We can use awk if we want to check coverage
  produce("bad_samples.txt")
  {
    exec """
      tail -n +2 $input.txt |
      awk -F "\t" '{if (\$2 < 30.0) print \$0;}' > $output.txt
    """
  }
}

report_coverage =
{
  def bad_samples = file(input).text
  if ( ! bad_samples.empty )
  {
    send report("report-template.html") to channel: gmail, file: input.txt, subject: "I have bad news ..."
    fail "SOME SAMPLES DID NOT REACH 30X COVERAGE"
  }
}

run { mean_target_coverage + verify_coverage + report_coverage }
