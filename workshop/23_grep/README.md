## 23. GREP

The grep statement is an internal convenience function that processes the input file line by line for each line matching a given regular expression.

I like to use the unix grep:

```
bpipe run unix_grep.groovy test.intervals
```

But you can also use this bpipe method:

```
bpipe run grep.groovy test.intervals
```