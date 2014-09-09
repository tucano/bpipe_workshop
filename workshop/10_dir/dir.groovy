about title: "Bpipe dir"

hello =
{
  output.dir = "out_dir"
  def myOutput = output.txt
  exec "cp $input.csv $myOutput"
}

run { hello }
