## 7. REGIONS and CHROMOSOMES

The **chr** statement splits execution into parallel paths based on chromosome. 

For the general case, all the files supplied as input are forwarded to every set of parallel stages supplied in the following list.

The stages that are executed in parallel receive a special implicit variable, **'chr'** that can be used to reference the chromosome that should be processed. This is typically passed through to tools that can confine their operations to a genomic region as an argument.

Explore chr and region variables

### chr.groovy

Simple parallelization based on chromosome.

You can remove/add chromosomes from the chr() method.

Notice the use of **RANGES**: `[1..5]`

```
bpipe run chr.groovy
```

### split_bam.groovy

Parallelizzation on regions to split a bam file by chromosomes

```
bpipe run chr.groovy
```

Notice the use of:

```
filter("$chr") { CODE_BLOCK }
```

To specify generate output files like: **sample.chr1.bam**



### gatk_call_variants.groovy

Many bioinformatics softwares support an "INTERVAL" option. 

An example is the GATK toolkit with the option **-L**:

	--intervals / -L
	One or more genomic intervals over which to operate
	Use this option to perform the analysis over only part of the genome. This argument can be specified multiple times. You can use samtools-style intervals either explicitly on the command line (e.g. -L chr1 or -L chr1:100-200) or by loading in a file containing a list of intervals (e.g. -L myFile.intervals). Additionally, you can also specify a ROD file (such as a VCF file) in order to perform the analysis at specific positions based on the records present in the file (e.g. -L file.vcf). Finally, you can also use this to perform the analysis on the reads that are completely unmapped in the BAM file (i.e. those without a reference contig) by specifying -L unmapped.


```
bpipe run gatk_call_variants.groovy
```

NOTE that also **bpipe** have a **-L option** to pass from command line the interval:

```
bpipe -L chr2 gatk_call_variants.groovy
```

### regions.groovy

It is often the case that you wish to run your pipeline but limit it to a particular genomic interval. For example, it may be that you wish to parallelize the pipeline over separate parts of the genome, or it might be that you are only interested in a subset of the genome to begin with.

Bpipe support genomic regions in this format:

```
bpipe -L chr1:1-10000 regions.groovy sample.bam
```

This has a few different effects:

1. A variable $region is available to your pipeline stages, carrying the value from the command line.
2. A variable $chr is available to your pipeline that specifies the chromosome from the region
3. You can reference $region.bed to reference the region in the form of a BED file. Bpipe creates this file on the fly for you when it is needed.


### regions_from_file.groovy

It is quite boring to support seceral regions from command line...

We can use a custom region variable (but we lose the region.bed feature)

```
bpipe run regions_from_file.groovy test.intervals
```


