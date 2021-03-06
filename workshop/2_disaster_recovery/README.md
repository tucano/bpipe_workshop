## 2. DISASTER RECOVERY

In this pipeline we have an error in the code:

```
cd disaster_recovery
bpipe run align.groovy ../../minify/sample1/*.fgz
```

In the merge stage we have an error (from PICARD):

```
========== Stage picard_merge ==========
ERROR: Option 'INPUT' must be specified at least 1 times

[...]

Pipeline failed! (2)

Command failed with exit status = 1 :

java -jar ~/libexec/picard/MergeSamFiles.jar O=sample1.merge.bam VALIDATION_STRINGENCY=SILENT CREATE_INDEX=false MSD=true ASSUME_SORTED=true USE_THREADING=true
```

Let's fix the error in code (add $input_strings) and we **retry the pipeline in TEST MODE**

```
bpipe retry test
```

```

============ Stage picard_merge ============


Abort due to Test Mode!

    Would execute: java -jar ~/libexec/picard/MergeSamFiles.jar I=sample1_L001_R1_001.bam I=sample1_L001_R1_002.bam I=sample1_L001_R1_003.bam I=sample1_L001_R1_004.bam O=sample1.merge.bam VALIDATION_STRINGENCY=SILENT CREATE_INDEX=false MSD=true ASSUME_SORTED=true USE_THREADING=true
```

With **test** bpipe doesn't execute the command, instead it builds the command for you and print in to STDOUT.

Now that we have checked that the command is correct, rerun with:

```
bpipe retry
```

Let's try the "move to trash if I am not sure" feature.
In the pipeline merge stage I have added a "false" to force fail of the stage "merge".

```
bpipe run trash_example.groovy ../../minify/sample1/*.fgz
```

The pipeline will fail with:

```
[...]

Cleaned up file sample1.merge.bam to .bpipe/trash/sample1.merge.bam
Pipeline failed! (2)

Command failed with exit status = 1 :

[...]
```

Bpipe have kindly moved my intermediate file to the trash dir for inspection.

Verify the bam file:

```
samtools view .bpipe/trash/sample1.merge.bam
```

Looks good, let's move it back to the working dir:

```
mv .bpipe/trash/sample1.merge.bam .
```

Now remove the "false" statement and retry the pipeline:

```
bpipe retry
```