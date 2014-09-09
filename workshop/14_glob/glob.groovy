about title: "Bpipe glob"

process_tsv =
{
  doc title : "Run a command with all CSV files in the local directory as input"

  from(glob("*.tsv")) produce("all_samples.csv")
  {
    exec """
      tail -q -n 2 $inputs | tr "\\\\t" "," > $output
    """
  }
}

run { process_tsv }
