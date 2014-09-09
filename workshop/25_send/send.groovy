about title: "A simple pipeline to align paired reads"

// USAGE: bpipe run -r align.groovy *.fagz
REFERENCE_GENOME = "../../minify/genome/chr22.fa"
PICMERGE         = "java -jar ~/libexec/picard/MergeSamFiles.jar"
MARKDUPLICATES   = "java -Djava.io.tmpdir=/tmp -jar ~/libexec/picard/MarkDuplicates.jar"

// THIS IS A STAGE
align_bwa =
{
  doc title: "Align DNA reads with bwa mem",
      desc: "Use bwa mem to align reads on the reference genome.",
      constraints: "Input must be compressed (fastq.gz)",
      author: "davide.rambaldi@gmail.com"

  requires REFERENCE_GENOME : "I need a reference genome (hg19.fa)"

  // We are going to transform FASTQ into a .bam file
  transform("bam")
  {
    exec """
      bwa mem $REFERENCE_GENOME $input1.fgz $input2.fgz |
      samtools view -bSu - |
      samtools sort - $output.bam.prefix;
    """
  }
}

picard_merge =
{
  doc title: "Merge BAM files",
      author: "davide.rambaldi@gmail.com"

  requires PICMERGE : "Please specify PICARD MergeSamFiles path"

  // lets use some groove to create the output filename stripping LANE and casava groups
  // NOTE THE USE OF def. def = scope of the variable is this stage, no propagation to other branches
  def sample_prefix = input.prefix.replaceAll(/_.*/,"")
  produce("${sample_prefix}.merge.bam")
  {
    def input_strings = inputs.collect() { return "I=" + it}.join(" ")
    exec """
      $PICMERGE $input_strings
        O=$output
        VALIDATION_STRINGENCY=SILENT
        CREATE_INDEX=false
        MSD=true
        ASSUME_SORTED=true
        USE_THREADING=true
    """
  }
}

mark_duplicates =
{
  doc title: "Mark Duplicates in BAM files",
      author: "davide.rambaldi@gmail.com"

  requires MARKDUPLICATES : "Please specify PICARD MarkDuplicates path"

  filter("dedup")
  {
    exec """
      $MARKDUPLICATES I=$input.bam O=$output.bam
        VALIDATION_STRINGENCY=SILENT
        CREATE_INDEX=true
        REMOVE_DUPLICATES=false
        ASSUME_SORTED=true
        METRICS_FILE=${output.prefix}.metrics
    """
  }
}

flagstat =
{
  doc title: "Stats on BAM file sent via email",
      author: "davide.rambaldi@gmail.com"

  transform("txt")
  {
    exec "samtools flagstat $input.bam > $output.txt"
    send report("report-template.html") to channel: gmail, file: output1.txt, subject: "This is custom bpipe email"
  }
}

// We can build report "inline"
inputs_report =
{
  send html {
    body {
      h1("This Email is from Bpipe inputs_report stage")
      table {
        tr { th("Inputs") }
        inputs.each { i -> tr { td(i) }  }
      }
    }
  } to channel: gmail, subject: "inputs report"
}

// HERE WE RUN THE PIPELINE
run {
  "%_R*_%.fgz" * [align_bwa] + inputs_report + picard_merge + mark_duplicates + flagstat
}
