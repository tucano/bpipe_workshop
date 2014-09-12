// THIS TITLE WILL BE USED IN REPORTS
about title: "Input and output variables"

// Our input is a bam file named sample.bam
inputs 'bam' : 'I need an input bam file, please'

merge = {
  exec "cp $input.bam $output.bam"
}

dedup = {
  exec "cp $input.bam $output.bam"
}

run { merge + dedup }
