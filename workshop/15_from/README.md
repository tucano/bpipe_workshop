## 15. FROM

The from statement reshapes the inputs to be the most recent output file(s) matching the given pattern for the following block. This is useful when a task needs an input that was produced earlier in the pipeline than the previous stage, or other similar cases where your inputs don't match the defaults that Bpipe assumes.

The patterns accepted by from are glob-like expression using * to represent a wildcard. A pattern with no wild card is treated as a file extension, so for example "csv" is treated as "*.csv", but will only match the first (most recent) CSV file. By contrast, using *.csv directly will cause all CSV files from the last stage that output a CSV file to match the first parameter. This latter form is particularly useful for gathering all the files of the same type output by different parallel stages.

Often a from would be embedded inside a produce, transform, or filter block, but that is not required. In such a case, from can be joined directly to the same block by preceding the transform or filter directly with the 'from' statement.

```
bpipe run from.groovy
```

