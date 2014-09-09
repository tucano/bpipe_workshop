about title: "Chromosome parallelization"

split_by_chromosome =
{
  println "Working on chromosome $chr"
}

run { chr(1..5) * [split_by_chromosome] }
