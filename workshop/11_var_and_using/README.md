## 11. VAR and USING

Define a variable with a default value, that can be overridden with a `using` construct, or by passing a value on the command line (`-p/--param` option).


```
bpipe run hello.groovy
bpipe run -p time=1 -p name=pippo hello.groovy
bpipe run -r var.groovy
bpipe run -p name="Mars" -r var.groovy
bpipe run -p name="Mars" -p greetings="Ciao" -r var.groovy
```

Generally this is used to send arguments to the bioinfomratics softwraes, so that a stage is more flexible.

I use also for my **"Pretend mode"** when I emulate pipeline on my laptop and I don;t want to really execute the commands (I just want to check the input/output flow)

```
bpipe run pretend.groovy *.fgz
bpipe run -p pretend=true pretend.groovy *.fgz
```
