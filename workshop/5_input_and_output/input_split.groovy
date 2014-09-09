split = {
  println "INPUT FILES ARE: $inputs"
}

run { split }
// run { "*" * [split] }
// run { "%" * [split] }
// run { "input_%_*.txt" * [split] }
// run { "_%_*." * [split] }
