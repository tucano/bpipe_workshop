## 6. PARALLEL TASKS

A frequent need in Bioinformatics pipelines is to execute several tasks at the same time. There are two main cases where you want to do this:

1. you have one set of data (eg. a sample) that needs to undergo several independent operations that can be done at the same time

2. your data is made up of many separate samples which can be processed independently through part or all of your pipeline

In both cases you can save a lot of time by doing the operations in parallel instead of sequentially. Bpipe supports this kind of parallel execution with a simple syntax that helps you declare which parts of your pipeline can execute at the same time and what inputs should go to them.

File: worlds.groovy

Explore different parallelizations with

```
bpipe run worlds.groovy
bpipe query
bpipe -f png diagram
```