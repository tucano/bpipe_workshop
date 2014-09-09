about title: "Let's play with regions from a file"

// We want somethins like this
example_regions = [
  'chr1:1-10000',
  'chr2:1-10000'
]

// But we will load it from a file (interval_list) using groovy
// Note that this is OUT of PIPELINE STAGES
def regions = []
new File(args[0]).eachLine { line ->
  regions << line
}

region_stage =
{
  branch.region = branch.name
  println "I am BRANCH with name ${branch.name} and I will work on region ${branch.region}"
}

run {
  regions * [region_stage]
}
