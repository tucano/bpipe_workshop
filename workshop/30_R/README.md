## 30. R

Executes the embedded R code by passing it to the Rscript utility. Tokens preceded with $ that match known Bpipe variables are evaluated as Bpipe variables before passing the script through. Other such tokens are passed through unmodified. This has the effect that you can reference your Bpipe variables such as $input and $output directly inside your R code.

**Note**: Any variable defined in the Bpipe / Groovy namespace will get evaluated, including pipeline stages, parameters passed to the script, and others. So it is important to understand that this can lead to unexpected evaluation of variables inside the R code. This feature is intended as a simple way to inline small R scripts, for example, to quickly create a plot of some results. Larger R programs should be executed by saving them as files and running them directly using Rscript.

Note: By default Bpipe uses the R executable (or actually, the Rscript executable) that it finds in your path. If you want to set a custom R executable, you can do so by adding a **bpipe.config** file with an entry such as the following:

```
R {
    executable = "/usr/local/R/3.1.1/bin/Rscript"
}
```

Simple Example:

```
bpipe run rscript.groovy
```

#### PLOT COVERAGE FROM HSMETRICS

In this example we start from a file: **samples_hsmetrics.txt** generated merging together the HsMetrics from different samples.

SEE: [http://picard.sourceforge.net/picard-metric-definitions.shtml](http://picard.sourceforge.net/picard-metric-definitions.shtml)

In the script we do the following steps:

1. load hsmetrics data froma file ($input.tsv)
2. Extract columns: PCT_TARGET_BASES_10X, PCT_TARGET_BASES_20X, PCT_TARGET_BASES_30X
3. Boxplot