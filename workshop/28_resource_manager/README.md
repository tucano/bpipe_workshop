## 28. RESOURCE MANAGER


See also: [https://code.google.com/p/bpipe/wiki/ResourceManagers](https://code.google.com/p/bpipe/wiki/ResourceManagers)


In some environments commands cannot be issued directly but must be queued and run in a managed environment that controls how long they can run for and what resources (memory, CPU, storage space, etc) they can use. Bpipe supports integration with third-party Resource Manager software to run commands that are part of your pipeline in this kind of environment.

Out of the box Bpipe supports:

* Torque PBS
* PBS Pro
* Sun / Oracle Grid Engine
*  Platform LSF

Others can be integrated if you implement a simple adapter shell script that can relay commands between Bpipe and the resource manager software.

To implement a resource manager: [https://code.google.com/p/bpipe/wiki/ImplementingAResourceManager](https://code.google.com/p/bpipe/wiki/ImplementingAResourceManager)

To make Bpipe use a Resource Manager to execute commands, you make a small file in the local directory (the same place as your pipeline script) called "bpipe.config". In this script you can place a single line that names the resource manager - for example:

```
executor="torque"
```

The following options are supported:

* walltime - how long the job is allowed to run. See below for format details
* procs - number of processors
* queue - the queue to use (default = "batch")
* memory - amount of memory to reserve (GB). Eg: "4"
* account - the account under which to run the command

