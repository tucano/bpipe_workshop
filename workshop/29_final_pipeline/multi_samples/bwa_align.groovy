about title: "A simple pipeline to align paired reads"

// USAGE: bpipe run -r align.groovy *.fagz
REFERENCE_GENOME = "../../../minify/genome/chr22.fa"
PICMERGE         = "java -jar ~/libexec/picard/MergeSamFiles.jar"
MARKDUPLICATES   = "java -Djava.io.tmpdir=/tmp -jar ~/libexec/picard/MarkDuplicates.jar"
BWA              = "/usr/local/bin/bwa"
SAMTOOLS         = "/usr/local/bin/samtools"

// 1. We slurp the json file passed as argument
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)


prepare =
{
  branch.sample = branch.name
  // OUTPUT DIR IS THE SAMPLE DIR
  output.dir    = branch.sample

  // create a scratch dir if doesn't exists and copy SampleSheet.csv
  produce("SampleSheet.csv")
  {
    exec """
      mkdir -p ${branch.sample};
      cp $input.csv $output.csv
    """
  }
}

align_bwa =
{
  // OUTPUT DIR IS THE SAMPLE DIR
  output.dir = branch.sample

  doc title: "Align DNA reads with bwa mem",
      desc: "Use bwa mem to align reads on the reference genome.",
      constraints: "Input must be compressed (fastq.gz)",
      author: "davide.rambaldi@gmail.com"

  requires REFERENCE_GENOME : "I need a reference genome (hg19.fa)"
  requires BWA : "Please specify PATH of bwa"
  requires SAMTOOLS : "Please specify PATH of samtools"

  // We are going to transform FASTQ into a .bam file
  transform("bam")
  {
    exec """
      $BWA mem $REFERENCE_GENOME $input1.fgz $input2.fgz |
      $SAMTOOLS view -bSu - |
      $SAMTOOLS sort - $output.bam.prefix;
    """
  }
}

picard_merge =
{
  // OUTPUT DIR IS THE SAMPLE DIR
  output.dir = branch.sample

  doc title: "Merge BAM files",
      author: "davide.rambaldi@gmail.com"

  requires PICMERGE : "Please specify PICARD MergeSamFiles path"

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
  // OUTPUT DIR IS THE SAMPLE DIR
  output.dir = branch.sample

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
  // OUTPUT DIR IS THE SAMPLE DIR
  output.dir = branch.sample
  doc title: "Stats on BAM file sent via email",
      author: "davide.rambaldi@gmail.com"

  transform("txt")
  {
    exec "samtools flagstat $input.bam > $output.txt"
    send report("report-template.html") to channel: gmail, file: output1.txt, subject: "This is custom bpipe email"
  }
}

run
{
  branches * [
    prepare + "%_R*_%.fgz" * [align_bwa] + picard_merge + mark_duplicates + flagstat
  ]
}
