about title: "Bpipe r scripting"

inputs 'txt' : 'Hsmetrics output for samples'

rscript =
{
  doc title: "Hsmetrics coverage distribution with R",
      desc: "Plot hsmetrics covergae distribution",
      constraints: "Only 10X-20X-30X",
      author: "davide.rambaldi@gmail.com"

  // We can use produce as any other bpipe stage
  produce("coverage.pdf")
  {
    // Notice that we have to escape $ to avoid bpipe binding
    // Notice that this will be binded with bpipe vars, notice use of "$output"
    R{"""
      pdf("$output")
      hsmetrics_data <- read.delim("$input.txt", stringsAsFactors=FALSE, row.names=1)
      covdata <- cbind(
        hsmetrics_data$PCT_TARGET_BASES_10X,
        hsmetrics_data$PCT_TARGET_BASES_20X,
        hsmetrics_data$PCT_TARGET_BASES_30X
      )
      colnames(covdata) <- c("10X","20X","30X")
      boxplot(covdata * 100, ylab="% of target bases", xlab="Coverage", col="grey")
      dev.off()
    """}
  }
}


run { rscript }
