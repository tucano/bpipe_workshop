## 13. PRODUCE

The produce statement declares a block of statements that will be executed transactionally to create a given set of outputs.

In this context, "transactional" means that all the statements either succeed together or fail together so that outputs are either fully created, or none are created (in reality, some outputs may be created but Bpipe will move such outputs to the trash folder). 
 
Although you do not need to use produce to use Bpipe, using produce adds robustness and clarity to your Bpipe scripts by making explicit the outputs that are created from a particular pipeline stage. This causes the following behavior:

* If a statement in the enclosed block fails or is interrupted, the specified outputs will be "cleaned up", ie. moved to the trash folder
* The implicit variable 'output' will be defined as the specified value(s), allowing it to be used in the enclosing block
* The specified output will become the default input to the next stage in the pipeline
* If the specified output already exists and is newer than all the input files then the produce block will not be executed.

```
bpipe run produce.groovy
```

A wildcard pattern can also be provided as input to produce. In such a case, the $output variable is not assigned a value, but after the produce block executes, the file system is scanned for files matching the wild card pattern and any files found that were not present before running the command are treated as outputs of the block.

Note: as Bpipe assumes ALL matching new files are outputs from the produce block, using a wild card pattern inside a parallel block should be treated with caution, as multiple executing pathways may "see" each other's files.

```
bpipe run wildcards.groovy
```

### DEFINE outputs

A common issue is that Bpipe's built in logic for transform and filter don't match what you want or expect for how the file names should come out. For example, some tools don't let you specify the output files and just make up their own based on some built in logic. Other times you have a mapping from input file to output file that is more complicated than a simple one to one (or one to many) that Bpipe allows for. And sometimes it is just a matter of personal preference for how the file names should look.

```
bpipe run define_outputs.groovy raw_data/*.fgz
```

#### HANDLING DIFFERENT FASTQ COMPRESSIONS WITH PRODUCE

I use the manual definition of outputs to handle different types of compression in an align stage.

```
bpipe run align.groovy raw_data/*.fgz
bpipe run align.groovy raw_data/*.fastq.gz
bpipe run align.groovy raw_data/*.fqz
bpipe run align.groovy raw_data/*.fastq
```

see also: [FQZ_COMP](http://sourceforge.net/projects/fqzcomp/)