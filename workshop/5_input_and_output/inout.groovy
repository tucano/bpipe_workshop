// THIS TITLE WILL BE USED IN REPORTS
about title: "Input and output variables"

// Our input is a bam file named sample.bam
// we can declare the input

inputs 'bam' : 'I need an input bam file, please'

// VERSION 1
merge = {
  exec "cp $input $output"
}

dedup = {
  exec "cp $input $output"
}

run { merge + dedup }

// VERSION 2
// merge = {
//   exec "cp $input.bam $output.bam"
// }

// dedup = {
//   exec "cp $input.bam $output.bam"
// }

// run { merge + dedup }

// VERSION 3
// @Filter("merge")
// merge_version_three =
// {
//   exec "cp $input.bam $output.bam"
// }

// @Filter("dedup")
// dedup_version_three =
// {
//   exec "cp $input.bam $output.bam"
// }

// run { merge_version_three + dedup_version_three }
