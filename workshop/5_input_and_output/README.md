## 5. INPUT AND OUTPUT

#### $INPUT AND $OUTPUT

In this example you can see that our commands still look like normal shell commands that you might have executed at the command line, however there are two conspicuous additions that look like shell variables: **$input** and **$output**. 

These variables are defined for you implicitly by Bpipe before each pipeline stage is run. 

They automatically tell you where the input is coming from and where the output should be going. 

You could, of course, hard code the file names into your commands, but then your pipeline would depend on those file names and you would not be able to "plug and play" with your pipeline stages to connect them in different ways. 

Since you aren't specifying the output file you might wonder where the actual output went. 

Bpipe uses a systematic naming convention for files so that they are reliably the same for any given pipeline configuration but reliably different if you change the pipeline. 

To achieve this, Bpipe builds file names by naming them according to all the pipeline stages through which the file has "passed", starting with the input file as a base.

This is really similar to what bioinformaticians do in real life, when they rename a BAM file merged and dith duplicates marked: **sample1.merge.dedup.bam**

```
bpipe run inout_v1.groovy sample.bam
```

#### EXTENSION METHODS

Bpipe offers a quick and simple way to specify file types for input and output files: adding the extension of the type of file to the **$input** and **$output** variables.

```
bpipe run inout_v2.groovy sample.bam
```

#### STAGES ANNOTATION: @FILTER AND @TRANSFORM

While the above pipeline works we can make it just a little better by giving Bpipe a few small hints about what each stage in the pipeline is doing. 


* **FILTER** : modifying a file without changing its type 
* **TRANSFORM** : operations that change the format or convert it to a different format

```
bpipe run inout_v3.groovy sample.bam
```

Let's experiment input and output features in a real stage:

```
bpipe run -r dedup.groovy sample.bam
```

#### INPUT SPLITTING PATTERNS: % and *

Bpipe uses a very simple wildcard pattern syntax to let you indicate how your files should be split into groups for processing. 

In these patterns you simply replace the portion of file names that indicates what group the file belongs to with the **percent character** which **acts as a wildcard** (matches any number of characters). 

***Files that share the same grouping portion will be passed together to the the parallel pipeline stages to process.***

Bpipe supports one other special character in its input splitting patterns: **the wildcard**. 

This also acts as a wildcard match but it does not split the input into groups. Instead, it affects ordering within the groups that are split. When Bpipe matches a * character in an input splitting pattern it first splits the files into their groups (based on the % match) and then sorts them based on the portions that match the * character. This helps you ensure that even after splitting, your files are still in a sensible order.

```
bpipe run input_split.groovy input_*.txt
```

#### MANUALLY DEFINE INPUTS

If you don't get the flexibility you need from the above mechanisms, you can set the branch paths yourself explicitly by specifying a Groovy List or a Map that tells Bpipe what paths you want. 

When you specify a Map, the keys in the map are interpreted as branch names and the values in the Map are interpreted as files, or lists of files, that are supplied to the branch as input.

```
bpipe run input_branches.groovy
```

Of course is quite boring writing groovy maps manually in the pipeline. But we can use a file as input **loading it with groovy out of the pipeline**.

Consider this typical structure:

```
.
├── sample1
│   ├── SampleSheet.csv
│   ├── sample1_L001_R1_001.fgz
│   ├── sample1_L001_R1_002.fgz
│   ├── sample1_L001_R1_003.fgz
│   ├── sample1_L001_R1_004.fgz
│   ├── sample1_L001_R2_001.fgz
│   ├── sample1_L001_R2_002.fgz
│   ├── sample1_L001_R2_003.fgz
│   └── sample1_L001_R2_004.fgz
└── sample2
    ├── SampleSheet.csv
    ├── sample2_L001_R1_001.fgz
    ├── sample2_L001_R1_002.fgz
    ├── sample2_L001_R1_003.fgz
    ├── sample2_L001_R1_004.fgz
    ├── sample2_L001_R2_001.fgz
    ├── sample2_L001_R2_002.fgz
    ├── sample2_L001_R2_003.fgz
    └── sample2_L001_R2_004.fgz
```

We want to align each sample in a separate directory, aligning reads in pair.

Let's use a simple groovy script to generate a **input.json** file:

```
./explicit_branch_map.groovy raw_data/sample*/*.fgz > input.json
```

And we have the Map ready to be loaded in a pipeline:

```
bpipe run branches.groovy input.json
```
