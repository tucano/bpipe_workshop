## 12. FILTER AND TRANSFORM

**FILTER:** In general you will use **filter** where you are keeping the same format for the data but performing some operation on the data.

**TRANSFORM:** If you have a command that converts a CSV file called foo.csv to an XML file, you can easily declare a section of your script to output foo.xml using a transform with the name 'xml'.

```
bpipe run filter_and_transform.groovy test.tsv
```

Since version 0.9.8.4, transform has offered an extended form that allows you to do more than just replace the file extension. This form uses two parts, taking the form:

```
transform(<input file pattern>) to(<output file pattern>) { ... }
```

The input and output patterns are assumed to match to the end of the file name, but can include a regular expression pattern for matching the input files.

```
bpipe run transform_to.groovy sample1_L001_R1_001.fastq.gz
```
