// RUN WITH: bpipe -p region=chr1:1-10000 arbitrary_regions.groovy example_input.bam
about title: "Let's play with regions"

// EXPERIMENTAL FEATURE "REQUIRES" INPUTS
inputs 'bam' : 'A bam file that you want to split'

region_stage =
{
  println "I am BRANCH with name ${branch.name} and I will work on region $region"

  filter("$region")
  {
    exec "touch $output"
  }
}

run {
  region_stage
}
