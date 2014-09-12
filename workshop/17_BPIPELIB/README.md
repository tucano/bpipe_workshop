## 17. BPIPE_LIB

Bpipe looks in a folder in your home directory called "bpipes" for files that can define pipeline stages. When you run bpipe, it loads all the pipeline stages from any file in this folder that ends with the ".groovy" file extension. When you save such files, all the pipeline stages and variables defined in there become available to any pipelines that you run.

Let's define it with BPIPE_LIB suing the subdir of 17_BPIPELIB

```
cd 17_BPIPELIB
cd bpipe_common_modules
echo $BPIPE_LIB
export BPIPE_LIB=`pwd`
echo $BPIPE_LIB
cd ..
bpipe run pipeline.groovy ../../minify/sample1/*.fgz
```
