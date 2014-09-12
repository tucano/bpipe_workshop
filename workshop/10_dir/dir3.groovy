about title: "Bpipe dir"

// OVERRIDE THE OUTPUT DIRECTORY
hello =
{
  output.dir = "out_dir"
  exec "cp $input.csv $output.txt"
}

run { hello }
