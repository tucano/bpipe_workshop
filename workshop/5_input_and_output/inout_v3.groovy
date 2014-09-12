// THIS TITLE WILL BE USED IN REPORTS
about title: "Input and output variables"

// Our input is a bam file named sample.bam
inputs 'bam' : 'I need an input bam file, please'

@Filter("merge")
merge_devel =
{
  exec "cp $input.bam $output.bam"
}

@Filter("dedup")
dedup_devel =
{
  exec "cp $input.bam $output.bam"
}

run { merge_devel + dedup_devel }
