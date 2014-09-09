about title: "Bpipe produce"

basic_produce =
{
  produce ("foo.txt","bar.txt")
  {
    exec """
      echo Hello > $output1;
      echo World > $output2;
    """
  }
}

run { basic_produce }
