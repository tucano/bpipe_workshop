## 8. VARIABLES

* implicit variables - variables defined by Bpipe for you
* explicit variables - variables you define yourself

```
bpipe run variables.groovy example*
```

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

#### DEF

***Problem: Bpipe is confusing variables between my parallel pipeline stages !***

Bpipe is based on groovy which allows you to declare variables in a script without any prefix. For example, you can write something like this and it will appear to work just fine:

    SAMPLE_NAME=parse_sample_name(input.bam)
    
But there's a problem here: these variables are actually global variables. That means, if you run many pipeline stages at once, they will be all sharing the same value and overwriting each other's value. Hence you would find the variables being apparently confused between different parallel paths in your pipeline.

Fortunately there is a simple solution to this: you need to ad 'def' prior to your variables to make them local variables:

    def SAMPLE_NAME=parse_sample_name(input.bam)
    
    
 