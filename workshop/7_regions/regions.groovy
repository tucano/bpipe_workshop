// RUN WITH: bpipe -L chr1:1-10000 regions.groovy sample.bam
about title: "Let's play with regions"

// EXPERIMENTAL FEATURE "REQUIRES" INPUTS
inputs 'bam' : 'A bam file that you want to split'

region_stage =
{
  println "I am BRANCH with name ${branch.name} and I will work on region $region"

  println "REGION AS BED FILE (in case you need it): ${region.bed}"

  filter("$region")
  {
    exec "touch $output"
  }
}

run {
  region_stage
}
