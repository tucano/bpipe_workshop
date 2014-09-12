## 20. LOAD


Imports the pipeline stages, variables and functions in the specified file into your pipeline script.

Currently the contents of files loaded using load are not imported directly into the file as if included literally. Instead they are scheduled to be imported when you construct a pipline. This means that when you execute a run or segment command, Bpipe loads them at that point to make them available within the scope of the run or segment statement. The result of this is that you cannot refer to them with global scope directly.

As of Bpipe 0.9.8.5, a file loaded with load can itself contain load statements so that you can build multiple levels of dependencies. Use this feature with caution, however, as there is no checking for cyclic depenencies, and thus it is possible to put Bpipe into an infinite loop by having two files that load each other.

```
bpipe run load.groovy
```
