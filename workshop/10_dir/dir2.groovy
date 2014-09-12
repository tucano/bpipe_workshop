about title: "Bpipe dir"

// With the **$input.dir** variable the dir is considered an input,
// and Bpipe will search for an input that is in fact a directory,
// rather than a file.
// This enables you to use the file-extension metaphor
// for selecting directories within your pipeline for input to your commands.
hello =
{
  println """
    INPUT FILE: $input.csv
    INPUT DIR: $input.dir
  """
}

run { hello }
