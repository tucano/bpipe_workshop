about title: "Bpipe filter and transform"

remove_comments =
{
  filter("nocomments")
  {
    exec """
      grep -v '^#' $input > $output
    """
  }
}

convert_to_csv =
{
  transform("csv")
  {
    // bash command is: tr "\\t" ","
    exec """
      cat $input | tr "\\\\t" "," > $output
    """
  }
}

run { remove_comments + convert_to_csv }
