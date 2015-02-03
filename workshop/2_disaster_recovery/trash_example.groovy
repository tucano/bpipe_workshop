about title: "A simple pipeline to align paired reads with syntax errors"

// USAGE: bpipe run -r align.groovy *.fagz
REFERENCE_GENOME   = "../../minify/genome/chr22.fa"
PICMERGE           = "java -jar /usr/local/cluster/bin/MergeSamFiles.jar"
MARKDUPLICATES     = "java -Djava.io.tmpdir=/tmp -jar /lustre1/tools/bin/MarkDuplicates.jar"
BWA                = "/lustre1/tools/bin/bwa"

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
    // The magic $threads variable used below will (by default) use 50% of all the available cores on your computer
    // for each alignment command (thus taking 100% of available threads).
    // You can control it by running using the -n command, eg, to run using 4 cores in total: bpipe run -n 4 align.groovy
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
        USE_THREADING=true;
      false
    """
    // Adding 'false' will return System Status 1, what will happend to the output file?
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

// HERE WE RUN THE PIPELINE
run {
  "%_R*_%.fgz" * [align_bwa] + picard_merge + mark_duplicates
}
