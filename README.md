# BPIPE

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

Bpip elog is based on `tail` unix command, then we have some useaful options:


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

Monitor all bpipe runs in the machine (login node):

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



## 2. DISASTER RECOVERY

In this pipeline we have an error in the code:

```
cd disaster_recovery
bpipe run align.groovy *.fgz
```

In the merge stage we have an error (from PICARD):

```
======================================== Stage picard_merge ========================================
ERROR: Option 'INPUT' must be specified at least 1 times

[...]

Pipeline failed! (2)

Command failed with exit status = 1 :

java -jar ~/libexec/picard/MergeSamFiles.jar O=sample1.merge.bam VALIDATION_STRINGENCY=SILENT CREATE_INDEX=false MSD=true ASSUME_SORTED=true USE_THREADING=true
```

Let's fix the error in code (re-add $input_strings) and we **retry the pipeline in TEST MODE**

```
bpipe retry test
```

```

======================================== Stage picard_merge ========================================


Abort due to Test Mode!

    Would execute: java -jar ~/libexec/picard/MergeSamFiles.jar I=sample1_L001_R1_001.bam I=sample1_L001_R1_002.bam I=sample1_L001_R1_003.bam I=sample1_L001_R1_004.bam O=sample1.merge.bam VALIDATION_STRINGENCY=SILENT CREATE_INDEX=false MSD=true ASSUME_SORTED=true USE_THREADING=true
```

With **test** bpipe doesn't execute the command, instead it builds the command for you and print in to STDOUT.

Now that we have checked that the command is correct, rerun with:

```
bpipe retry
```


## 3. GROOVY 

Install groovy and open the groovysh console for a better experience:

```
groovysh
```

See also: [CODEHAUS](http://groovy.codehaus.org/)


Hello world in groovy:

```
groovy:000> println "Hello, World!"
Hello, World!
===> null
```

Take a look at the file: **strings.groovy** this is pure groovy language with examples on string operations:

launch With groovy:

```
groovy strings.groovy
groovy lists.groovy
groovy maps.groovy
groovy files.groovy
```

Launch with bpipe

```
bpipe run strings.groovy
bpipe run lists.groovy
bpipe run maps.groovy
bpipe run files.groovy
```

## 4. EXEC

A minimal pipeline is composed by:

1. one or more stages
2. A "run" closure (the pipeline)

Each stage contains an *exec* command that runs the command in a bash shell.
Long commands that are passed to exec can be specified over multiple lines by using triple quotes. 

Bpipe automatically removes newlines from commands so that you do not need to worry about it.

An exec statement will often be embedded in a produce, filter or transform block to specify and manage the outputs of the command.

All commands executed via exec are automatically added to the command history for the run.

If the command fails or is interrupted (by Ctrl-C or kill), the pipeline will be automatically terminated. Any outputs specified as generated by the stage running the exec command will be marked as dirty and moved to the local bpipe trash folder.


```
cd 4_exec
```

Read/Comment/Run:

1. helloworld.groovy
2. exec.groovy


## 5. INPUT AND OUTPUT

In this example you can see that our commands still look like normal shell commands that you might have executed at the command line, however there are two conspicuous additions that look like shell variables: $input and $output. These variables are defined for you implicitly by Bpipe before each pipeline stage is run. They automatically tell you where the input is coming from and where the output should be going. You could, of course, hard code the file names into your commands, but then your pipeline would depend on those file names and you would not be able to "plug and play" with your pipeline stages to connect them in different ways. Since you aren't specifying the output file you might wonder where the actual output went. Bpipe uses a systematic naming convention for files so that they are reliably the same for any given pipeline configuration but reliably different if you change the pipeline. To achieve this, Bpipe builds file names by naming them according to all the pipeline stages through which the file has "passed", starting with the input file as a base.

Bpipe offers a quick and simple way to specify file types for input and output files: adding the extension of the type of file to the $input and $output variables.

Bpipe uses a very simple wildcard pattern syntax to let you indicate how your files should be split into groups for processing. In these patterns you simply replace the portion of file names that indicates what group the file belongs to with the percent character which acts as a wildcard (matches any number of characters). Files that share the same grouping portion will be passed together to the the parallel pipeline stages to process.

Bpipe supports one other special character in its input splitting patterns: the * wildcard. This also acts as a wildcard match but it does not split the input into groups. Instead, it affects ordering within the groups that are split. When Bpipe matches a * character in an input splitting pattern it first splits the files into their groups (based on the % match) and then sorts them based on the portions that match the * character. This helps you ensure that even after splitting, your files are still in a sensible order.

If you don't get the flexibility you need from the above mechanisms, you can set the branch paths yourself explicitly by specifying a Groovy List or a Map that tells Bpipe what paths you want. When you specify a Map, the keys in the map are interpreted as branch names and the values in the Map are interpreted as files, or lists of files, that are supplied to the branch as input.

Bpipe pipelines:

1. inout.groovy
2. input_split.groovy
3. dedup.groovy
4. input_branches.groovy


## 6. PARALLEL TASKS

File: worlds.groovy

Explore different parallelizations with

```
bpipe run worlds.groovy
bpipe query
bpipe -f png diagram
```

## 7. REGIONS and CHROMOSOMES

The chr statement splits execution into parallel paths based on chromosome. For the general case, all the files supplied as input are forwarded to every set of parallel stages supplied in the following list. (See below for an exception to this).

The stages that are executed in parallel receive a special implicit variable, 'chr' that can be used to reference the chromosome that should be processed. This is typically passed through to tools that can confine their operations to a genomic region as an argument.

Explore chr and region variables

1. chr.groovy
2. split_bam.groovy
3. gatk_call_variants.groovy


1. regions.groovy
2. regions_from_file.groovy


## 8 VARIABLES

* implicit variables - variables defined by Bpipe for you
* explicit variables - variables you define yourself

#### Implicit Variables

* input - defines the name(s) of files that are inputs to your pipeline stage
* output - defines the name(s) of files that are outputs from your pipeline stage
* branch - when a pipeline is running parallel branches, the name of the current branch is available in a $branch variable.

#### EXTENSION SYNTAX

When you use $input and $output you are asking for generic input and output file names from Bpipe, and it will give you names corresponding to defaults that it computes from the pipeline stage name. These, however, don't always end up with file extension that are very natural, and also may not be very robust because the type of file to be used is not specified. 

#### Differences from Bash Variable Syntax

Bpipe's variable syntax is mostly compatible with the same syntax in languages like Bash and Perl. This is very convenient because it means that you can copy and paste commands directly from your command line into your Bpipe scripts, even if they use environment variables.

However there are some small differences between Bpipe variable syntax and Bash variable syntax

Bpipe always expects a $ sign to be followed by a variable name. Thus operations in Bash that use $ for other things need to have the $ escaped so that Bpipe does not interpret it. 

#### PREFIX

Bpipe offers a "magic" ".prefix" extension that lets you refer to an output, but pass to the actual command the output trimmed of its suffix.

File: variables.groovy

#### BRANCH VARIABLES


#### VAR and USING


