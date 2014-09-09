about title: "Bpipe produce with wildcards"

wildcards_produce =
{
  produce ("*.csv")
  {
    exec """
      echo Hello > wild_foo.csv;
      echo World > wild_bar.csv;
    """
  }

}

run { wildcards_produce }
