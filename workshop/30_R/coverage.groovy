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
      covdata <- c(
        mean(hsmetrics_data\$PCT_TARGET_BASES_10X),
        mean(hsmetrics_data\$PCT_TARGET_BASES_20X),
        mean(hsmetrics_data\$PCT_TARGET_BASES_30X)
      )
      names(covdata) <- c("10X","20X","30X")
      bp <- barplot(covdata*100, las=1, ylim=c(0,100), col="grey", main="Cumulative distribution of target coverage")
      text(bp, 0, paste(round(covdata*100, digits=2),"%"),cex=1,pos=3)
      dev.off()
    """}
  }
}


run { rscript }
