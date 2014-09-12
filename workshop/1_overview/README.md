## 1. OVERVIEW

```
cd 1_overview
```

#### Run the pipeline:

```
bpipe run align.groovy ../../minify/sample1/*.fgz
```

#### Run the pipeline with report generation:

```
bpipe run -r align.groovy ../../minify/sample1/*.fgz
```

Your report is in doc/index.html, open it!

#### Log last pipeline run

```
bpipe log
```

Bpipe log is based on `tail` unix command, then we have some useaful options:


Log 1000 lines

```
bpipe log -n 1000
```

Continious Log `tail -f <file.log>`

```
bpipe log -f
```

#### Query Input/Output files

Query for all files:

```
bpipe query
```

Query a single file:

```
bpipe query sample1_L001_R1_003.bam
```

#### CLEANUP INTERMEDIATE FILES

remove all intermediate files:

```
bpipe cleanup
```

What you want to do?

```
> remove/trash these files? (y/t/n)
```

* y = Yes
* n = No (Cancel)
* t = move to .bpipe/thrash

Just remove my intermediate files (no confirmation asked):

```
bpipe cleanup -y
```

#### HISTORY OF LAST BPIPE RUNS

```
bpipe history
```

#### STOP GRACEFUL THE PIPELINE

```
bpipe stop
```

#### BPIPE JOBS

Monitor all bpipe runs in the machine:

```
bpipe jobs
```

#### commandlog.txt

The command log is stored in a file called commandlog.txt in the local directory where the job is running. It is a file that is continuously appended to as each and every job is run to create an ever growing log of every command that was executed.

```
####################################################################################################
# Starting pipeline at Wed Sep 03 10:57:22 CEST 2014
# Input files:  [../../minify/sample1/sample1_L001_R1_001.fgz, ../../minify/sample1/sample1_L001_R1_002.fgz, ../../minify/sample1/sample1_L001_R1_003.fgz, ../../minify/sample1/sample1_L001_R1_004.fgz, ../../minify/sample1/sample1_L001_R2_001.fgz, ../../minify/sample1/sample1_L001_R2_002.fgz, ../../minify/sample1/sample1_L001_R2_003.fgz, ../../minify/sample1/sample1_L001_R2_004.fgz]
# Output Log:  .bpipe/logs/9437.log
# Stage align_bwa [sample1_L001]
# Stage align_bwa [sample1_L001]
# Stage align_bwa [sample1_L001]
# Stage align_bwa [sample1_L001]
bwa mem ../../minify/genome/chr22.fa ../../minify/sample1/sample1_L001_R1_002.fgz ../../minify/sample1/sample1_L001_R2_002.fgz | samtools view -bSu - | samtools sort - sample1_L001_R1_002;
bwa mem ../../minify/genome/chr22.fa ../../minify/sample1/sample1_L001_R1_001.fgz ../../minify/sample1/sample1_L001_R2_001.fgz | samtools view -bSu - | samtools sort - sample1_L001_R1_001;
bwa mem ../../minify/genome/chr22.fa ../../minify/sample1/sample1_L001_R1_003.fgz ../../minify/sample1/sample1_L001_R2_003.fgz | samtools view -bSu - | samtools sort - sample1_L001_R1_003;
bwa mem ../../minify/genome/chr22.fa ../../minify/sample1/sample1_L001_R1_004.fgz ../../minify/sample1/sample1_L001_R2_004.fgz | samtools view -bSu - | samtools sort - sample1_L001_R1_004;
# Stage picard_merge
java -jar ~/libexec/picard/MergeSamFiles.jar I=sample1_L001_R1_001.bam I=sample1_L001_R1_002.bam I=sample1_L001_R1_003.bam I=sample1_L001_R1_004.bam O=sample1.merge.bam VALIDATION_STRINGENCY=SILENT CREATE_INDEX=false MSD=true ASSUME_SORTED=true USE_THREADING=true
# Stage mark_duplicates
java -Djava.io.tmpdir=/tmp -jar ~/libexec/picard/MarkDuplicates.jar I=sample1.merge.bam O=sample1.merge.dedup.bam VALIDATION_STRINGENCY=SILENT CREATE_INDEX=true REMOVE_DUPLICATES=false ASSUME_SORTED=true METRICS_FILE=sample1.merge.dedup.metrics
```

#### EXPLORING THE .BPIPE DIRECTORY

For a local run (no cluster):

```
.bpipe
├── checks
├── commands
├── executed
├── inprogress
├── jobs
├── launch
├── logs
└── trash
```

* **thrash**: where you find broken intermediate files

Clean your bpipe directory `rm -rf .bpipe` when you finish the pipeline!
