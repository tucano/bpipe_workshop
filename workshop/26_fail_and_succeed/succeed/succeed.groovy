about title: "Bpipe succeed"

check_coverage =
{
  var minimal_cov : 30.0

  if ( file(input).text.toBigDecimal() < minimal_cov )
  {
    fail "SAMPLE ${branch.name} DID NOT REACH 30X COVERAGE"
  }
  else
  {
    succeed "SAMPLE ${branch.name} IS OK"
  }
}

run { "%.sample.txt" * [ check_coverage ] }
