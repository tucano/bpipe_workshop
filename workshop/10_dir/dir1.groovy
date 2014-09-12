about title: "Bpipe dir"

// When you reference $output.dir Bpipe treats it as a reference to the output file,
// but it passes the directory in which the file resides to the command that is executing
hello =
{
  def myOutput = "out_dir/$input"
  produce(myOutput) {
    exec "cp $input.csv $output.dir/"
  }
}

run { hello }
