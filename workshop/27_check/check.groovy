test_check =
{
  check {
        exec "[ -s $input ]"
  } otherwise {
        fail "The output file had zero length"
  }
}

run { test_check }
