## 14. GLOB

The glob function is useful if you want to match a set of files as inputs to a pipeline stage and you are not able to achieve it using the normal Bpipe input variables. For example, in situations where you want to match a complicated set of files, or files that are drawn from across different pipeline branches that don't feed their inputs to each other. A call to glob can be used inside a from statement to feed the matched files into the input variables so that they can be referenced in exec and other statements in pipeline stages.

```
bpipe run glob.groovy
```