about title: "Bpipe grep function"

split_intervals =
{
  produce("${input.prefix}.${chr}.txt")
  {
    grep("$chr:") { line ->
      out << line << "\n"
    }
  }
}

run { chr(1..22,'X') * [split_intervals] }
