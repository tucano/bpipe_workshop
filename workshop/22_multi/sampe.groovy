about title: "Bpipe alignment with sampe"

// The reference to use
REF = "../../minify/genome/chr22.fa"

align_bwa =
{
  // We are going to transform FASTQ into two .sai files and a .bam file
  transform("sai","sai","bam")
  {
    // Alignment with bwa consists of two separate commands:
    //
    //   1. finding the SA coordinates by running bwa aln
    //   2. converting coordinates to actual read alignments and
    //      matching ends together with bwa sampe

    // Step 1 - run both bwa aln commands in parallel
    multi "gunzip -c $input1.gz |  bwa aln -t $threads $REF - > $output1",
          "gunzip -c $input2.gz |  bwa aln -t $threads $REF - > $output2"
    // the magic $threads variable used below will (by default) use 50% of all the available cores on your computer
    // for each alignment command (thus taking 100% of available threads).
    // You can control it by running using the -n command, eg, to run using 4 cores in total: bpipe run -n 4 pipeline.groovy

    // Step 2 - bwa sampe
    exec "bwa sampe $REF $output1 $output2 $input1.gz $input2.gz | samtools view -bSu - | samtools sort - $output.bam.prefix"
  }
}

// Single sample, simple execution, no parallelism
run { align_bwa }

// Multiple samples where file names begin with sample
// name and are separated by underscore from the rest of the
// file name
// run { "%_*.fastq.gz" * [ align_bwa ] }
