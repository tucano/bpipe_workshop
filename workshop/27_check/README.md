## 27. CHECK

The check statement gives a convenient way to implement validation of a pipeline's outputs or progress and implement an alternative action if the validation fails. The check clause is executed and any exec or other statements inside are processed. If one of these fails, then the otherwise clause executes.

A convenient use of check is in conjunction with **success**, **fail** and **send** commands.

Note 2: due to a quirk of Groovy syntax, the otherwise command must be placed on the same line as the preceding curly bracket of the check clause.

```
bpipe run check.groovy test.txt
```

```
bpipe run check.groovy test2.txt
```