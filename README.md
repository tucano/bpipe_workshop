# BPIPE WORKSHOP PROGRAM

See README for each session in `workshop/`


### PREREQUISITES FOR THE WORKSHOP

A laptop with installed or avaliable remotly:

1. bpipe (https://code.google.com/p/bpipe/)
2. bwa (http://bio-bwa.sourceforge.net/)
3. Picard (http://picard.sourceforge.net/command-line-overview.shtml)
4. ~1 GB of hard disk space (for input example files and intermediate file)

I plan to make a presentation of ~60 minutes and several tutorials  (workshop)

### GENERAL PRESENTATION (60 minutes)

bpipe: A framework to launch bioinformatic pipelines (KeyNote presentation)

### WORKSHOP SESSIONS (300 minutes)

1. Overview: bpipe command line utility, the .bpipe directory, Logging, HTML reports (30 minutes)
2. Basic Groovy language: Operation on Strings, Lists, Maps, Files (30 minutes)
3. HelloBpipe: write your first pipelines with bpipe: Exec, Inputs and Outputs, Parallel tasks (30 minutes)
4. Bpipe variables: extension syntax, prefix, dir, branch variables, var, using (30 minuts)
5. Controlling your output: filter, transform, produce, glob, forward, preserve, from (30 minutes)
6. Documenting your pipeline, check prerequisites, load external stages: doc, about, requires, load, BPIPE_LIB (30 minutes)
7. Control pipeline flow and send notification: Notifications and Emails, send, check, fail, succeed (30 minutes)
8. From abstract pipelines to real cluster jobs: Resource Managers (30 minutes)
9. Real bioinformatic pipelines: an example pipeline with bwa, MergeSamFile, MarkDuplicates and a minified test genome (30 minutes)
10. Advanced users: creating reports with bpipe, R and knitr (30 minutes)


### SENDING EMAIL

For security reasons I have changed the password of the user:

bpipe.workshop@gmail.com

In order to test the email system you should create a gmail account and add this info in the bpipe.config file:

```
notifications {
    gmail {
        to="your.address@example.com"
        username="bpipe.email@gmail.com"
        password="Password of bpipe email"
        events="FINISHED"
    }
}
```