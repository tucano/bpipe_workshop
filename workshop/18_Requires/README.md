## 18. REQUIRES

Specify a parameter or variable that must be provided for this pipeline stage to run. The variable can be provided with a Using construct, by passing a value on the command line (--param option), or simply by defining the variable directly in the pipeline script. If the variable is not defined by one of these mechanisms, Bpipe prints out an error message to the user, including the message defined by the requires statement.

Let's redefine BPIPE_LIB

```
cd 18_Requires
cd bpipe_common_modules
echo $BPIPE_LIB
export BPIPE_LIB=`pwd`
echo $BPIPE_LIB
```

First run:

```
bpipe run pipeline.groovy ../../minify/sample1/*.fgz
```

Now modify modules and add requires, second run:

```
bpipe run pipeline.groovy ../../minify/sample1/*.fgz
```

Now add missing variables and run again.