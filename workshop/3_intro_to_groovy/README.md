## 3. GROOVY 

See also: [CODEHAUS](http://groovy.codehaus.org/)

Bpipe is compiled as Java code but is also a groovy interpreter (isa ble to compile groovy code due to the presence of the groovy.jar)

You can just exploit the bpipe.jar to load **groovy.lang.GroovyShell**

```
java -classpath /PATH_TO_BPIPE/lib/bpipe.jar  groovy.lang.GroovyShell example.groovy
```

You can launch the code as a bpipe pipeline (see below for code examples):

```
bpipe run example.groovy
```

or you can install groovy and use groovy:

```
groovy example.groovy
```

For a better experience, use the groovysh console 

```
groovysh
```

Hello world in groovysh:

```
groovy:000> println "Hello, World!"
Hello, World!
===> null
```

#### CLOSURES

##### File: closure.groovy

A Groovy Closure is like a "code block" or a method pointer.
It is a piece of code that is defined and then executed at a later point:

```
my_closure = {
  println "hello from my closure!"
}

my_closure()
```

Run this example with:

```
groovy closure.groovy
```

Without groovy installed try:

```
java -classpath /BPIPE_PATH/lib/bpipe.jar  groovy.lang.GroovyShell closure.groovy
```

##### File: stage.groovy

A bpipe **stage** is also a groovy closure, the only difference is that, instead of executing it directly, we **run** it with bpipe:

```
my_stage = {
  println "hello from my stage!"
}

run {
  my_stage
}
```

Run with:

```
bpipe run stage.groovy
```

Note the differences between **groovy** and the **bpipe DSL**:

1. The closure is not executed directly but is embedded in the method **Bpipe.run**
2. **run** is a method (there is no "=") with attached a **code block**.
3. In the **run** code block we put stages we want to execute without ()

We will see that in **run** we ca do special operations on stages like joining them with **+** (plus).

**DEV NOTE:**

Regular Java and Groovy blocks are executed the moment they are encountered; closures are only executed if the call() is invoked on the closure.


#### File: strings.groovy

Take a look at the file: **strings.groovy** this is pure groovy language with examples on string operations:

launch With groovy:

```
groovy strings.groovy
```

Launch with bpipe

```
bpipe run strings.groovy
```

#### File: lists.groovy

launch With groovy:

```
groovy lists.groovy
```

Launch with bpipe

```
bpipe run lists.groovy
```


#### File: maps.groovy

launch With groovy:

```
groovy maps.groovy
```

Launch with bpipe

```
bpipe run maps.groovy
```

#### File: files.groovy

launch With groovy:

```
groovy files.groovy
```

Launch with bpipe

```
bpipe run files.groovy
```

#### File: template.groovy

The SimpleTemplateEngine that allows you to use JSP-like scriptlets (see example below), script, and EL expressions in your template in order to generate parameterized text.

```
import groovy.text.SimpleTemplateEngine

def text = 'Dear "$firstname $lastname",\nSo nice to meet you in <% print city %>.\nSee you in ${month},\n${signed}'

def binding = ["firstname":"Sam", "lastname":"Pullara", "city":"San Francisco", "month":"December", "signed":"Groovy-Dev"]

def engine = new SimpleTemplateEngine()
template = engine.createTemplate(text).make(binding)

def result = 'Dear "Sam Pullara",\nSo nice to meet you in San Francisco.\nSee you in December,\nGroovy-Dev'

assert result == template.toString()
```

To use it, you must import (also in a bpipe stage): **groovy.text.SimpleTemplateEngine**

Launch the example with:

```
groovy template.groovy
```

or 

```
bpipe run template.groovy
```

