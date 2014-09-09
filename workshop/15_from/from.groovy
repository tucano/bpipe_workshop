about title: "Bpipe from"

make_some_csv_files =
{
  produce("foo.csv","bar.csv","doh.csv")
  {
    exec "touch $output1 $output2 $output3"
  }
}

make_some_csv_files2 =
{
  produce("foo2.csv","bar2.csv","doh2.csv")
  {
    exec "touch $output1 $output2 $output3"
  }
}

make_some_xml_files =
{
  produce("foo.xml","bar.xml","doh.xml")
  {
    exec "touch $output1 $output2 $output3"
  }
}

make_some_bam_files =
{
  produce("foo.bam","bar.bam","doh.bam")
  {
    exec "touch $output1 $output2 $output3"
  }
}

base =
{
  println "INPUTS: $inputs"
}

collect_all_csv_from_last_stage =
{
  from("*.csv") {
    println "INPUTS: $inputs"
  }
}

collect_one_csv =
{
  from("csv") {
    println "INPUTS: $inputs"
  }
}

collect_all_csv =
{
  from(glob("*.csv")) {
    println "INPUTS $inputs"
  }
}

collect_all_xml_from_last_stage =
{
  from("*.xml") {
    println "INPUTS: $inputs"
  }
}

run {
  [make_some_csv_files, make_some_xml_files] + make_some_csv_files2 + make_some_bam_files +
  base + collect_all_csv_from_last_stage + collect_one_csv + collect_all_csv + base + collect_all_xml_from_last_stage
}
