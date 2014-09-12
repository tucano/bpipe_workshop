## 10. DIR


Sometimes a command you want requires not the name of an output file, but the name of the directory in which that file resides. This can cause a bit of confusion to Bpipe, because the real output is the file, but what you reference in your command is the directory.

To help with this, Bpipe offers a special meaning for the "dir" extension. When you reference **$output.dir** Bpipe treats it as a reference to the output file, but it passes the directory in which the file resides to the command that is executing.

```
bpipe run dir1.groovy test.csv
```

The **$input.dir** variable also has special meaning. In this case the dir is taken to mean that the whole directory itself should be considered an input, and Bpipe will search for an input that is in fact a directory, rather than a file. This enables you to use the file-extension metaphor for selecting directories within your pipeline for input to your commands.

```
bpipe run dir2.groovy out_dir test.csv
```

While the input.dir is a fixed value, **output.dir is a writeable value, so you can use it to change the value of directory to which outputs will go**. Bpipe currently expects all outputs from a pipeline stage to go to the same directory, so setting this to multiple values or different values will not work.

```
bpipe run dir3.groovy out_dir test.csv
```

#### FASTQC EXAMPLE

Here we wish to have fastqc put its output zip file into a directory called "qc_data". However fastqc won't let us tell it the full path of the zip file, only the directory name.

```
bpipe run fastqc.groovy sample1*
```