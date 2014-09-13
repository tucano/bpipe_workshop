## 31. REPORTS

In this example we will use:

1. bpipe
2. SimpleTemplateEngine (groovy)
3. R
4. Knitr

To create an html report.

We use again the file **samples_hsmetrics.txt** generated merging together the HsMetrics from different samples.

SEE: [http://picard.sourceforge.net/picard-metric-definitions.shtml](http://picard.sourceforge.net/picard-metric-definitions.shtml)


The `knitr_report` stage make the following steps:

1. `import groovy.text.SimpleTemplateEngine`
2. Load a generic report template: `"template/report_template.Rmd.template"`
3. Load a **rationale.md** file (will contain the Ratoinale of the project)
4. Declare some outputs: `produce("report.Rmd","report.md","report.html")`
5. Bind variables to the generic template
6. Write a knitr R markdown file (`report.Rmd`)
7. Launch R with knitr to produce a report in markdown and html (`report.md` and `report.html`)

```
bpipe run knitr_report.groovy samples_hsmetrics.txt
```