## 26. FAIL AND SUCCEED

#### FAIL

Causes the current branch of the pipeline to terminate explicitly with a failure status and a provided message.

In the most simple form, a short message is provided as a string. The longer forms allow a notification or report to be generated as a result of the success.

While using fail as a stand alone construct is possible, the primary use case is to embed it inside the otherwise clause of a check command, which ensures that Bpipe remembers the status and output of the check performed.

```
cd fail/simple/
bpipe run fail.groovy samples_hsmetrics.txt
bpipe run parallel_fail.groovy samples/*.sample.txt
```

```
cd fail/with_report
bpipe run fail_and_report.groovy samples_hsmetrics.txt
```

#### SUCCEED

Causes the current branch of the pipeline (or the whole pipeline, if executed in the root branch) to terminate with a successful status, but without producing any outputs.

In the most simple form, a short message is provided as a string. The longer forms allow a notification or report to be generated as a result of the success.

The succeed command allows you to have a branch of your pipeline terminate without continuing or feeding outputs into any following stages. This is mostly useful in situations where you have many parallel stages running. In a normal case, Bpipe expects every parallel branch to produce an output, and will fail the entire pipeline if the expected outputs are not generated. Sometimes however, no outputs are legitimately produced and in that case you just want to stop processing in those branches that give no outputs while allowing others to continue without aborting the whole pipeline.

```
cd succeed
bpipe run succeed.groovy samples/*.sample.txt
```
