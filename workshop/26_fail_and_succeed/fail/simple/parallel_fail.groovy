about title: "Bpipe fail and parallelization"

check_coverage =
{
  var minimal_cov : 30.0

  if ( file(input).text.toBigDecimal() < minimal_cov )
  {
    fail "SAMPLE ${branch.name} DID NOT REACH 30X COVERAGE"
  }
}

run { "%.sample.txt" * [ check_coverage ] }
