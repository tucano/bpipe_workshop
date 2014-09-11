about title: "Bpipe reporting wiht template engine and knitr"

import groovy.text.SimpleTemplateEngine

inputs 'txt' : 'Hsmetrics output for samples'

RATIONALE = "data/rationale.md"
TEMPLATE  = "template/report_template.Rmd.template"

knitr_report =
{
  requires TEMPLATE  : "Please define a template for the report"
  requires RATIONALE : "Please define a rationale for the report (rationale.md)"

  produce("report.Rmd","report.md","report.html")
  {
    def binding = [
      "PROJECTNAME" : "BPIPE WORKSHOP",
      "AUTHOR"      : "davide.rambaldi@gmail.com",
      "RATIONALE"   : file("$RATIONALE").text,
      "METRICS"     : input.txt
    ]

    def engine = new SimpleTemplateEngine()
    def template = new File("$TEMPLATE").text
    def report = engine.createTemplate(template).make(binding)
    def report_rmd = new File("report.Rmd").write(report.toString())

    R{"""
      library(knitr)
      knitr::knit2html("report.Rmd")
    """}
  }
}

run { knitr_report }
