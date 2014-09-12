## 22. MULTI

The multi statement executes multiple commands in parallel and waits for them all to finish. If any of the commands fail the whole pipeline stage fails, and all the failures are reported.

Generally you will want to use Bpipe's built in parallelization features to run multiple commands in parallel. However sometimes that may not fit how you want to model your pipeline stages. The multi statement let's you perform small-scale parallelization inside your pipeline stages.

```
bpipe run multi.groovy
```

If you wish to pass a computed list of commands to multi, use the form multiExec instead (see example below).

```
bpipe run multiExec.groovy
```

A typical usage example is the **classic paired ends** alignment with `bwa aln` and `bwa sampe`:

```
bpipe run sampe.groovy sample_*.fastq.gz
```