about title: "Bpipe using unix grep caveats"

// 'grep' will return a failure exit status if it doesn't find what it is looking for.
// This is hardly a failure. You can prevent this by adding "|| true" to the end of your command.
split_intervals =
{
  produce("${input.prefix}.${chr}.txt")
  {
    exec """
      grep "${chr}:" $input > $output.txt
    """

    // exec """
    //   grep "${chr}:" $input > $output.txt || true
    // """
  }
}

run { chr(1..22,'X','Y') * [split_intervals] }
