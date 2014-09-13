## 29. A Real bioinformatic pipeline

Now that we have more or less all the pieces we need, we can study a real pipeline with the following features:


### SINGLE SAMPLE

1. Paired end alignment with bwa mem
2. Merge with picard
3. Mark Duplicates with picard
4. Stats with samtools flagstat
5. Reporting on alignment with send and a custom report

```
cd single_sample
bpipe run bwa_align.groovy ../../../minify/sample1/*.fgz
```


### MULTI SAMPLES

For each sample:

1. Paired end alignment with bwa mem
2. Merge with picard
3. Mark Duplicates with picard
4. Stats with samtools flagstat
5. Reporting on alignment with send and a custom report

```
cd multi_samples
bpipe run bwa_align.groovy input.json
```
