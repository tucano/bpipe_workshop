rscript =
{
  R{"""
    pdf("output.pdf")
    demo("graphics")
    dev.off()
  """}
}

run { rscript }
