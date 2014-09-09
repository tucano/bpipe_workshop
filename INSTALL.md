# BPIPE INSTALLATION

Bpipe is tested and maintained on:

Linux RHEL, Centos and Ubuntu
Mac OS X Snow Leopard or later
Windows using Cygwin

Bpipe is entirely self contained and doesn't require any installation.

1. unzip your Bpipe download to a convenient location
2. place the "bin" directory in your PATH.

Example (On MAC OSX)

1. download last version from: http://download.bpipe.org/   (0.9.8.6_rc)
1. mkdir -p ~/bin
2. tar xzvf bpipe-0.9.8.6_rc.tar.gz -C ~/bin
3. export PATH=~/bin/bpipe-0.9.8.6_rc/bin:$PATH (you can add this to your .profile file)
4. bpipe -h


# BPIPE SOURCE INSTALL

Bpipe is a groovy software compiled as a java jar package. In order to compile the software you need git, groovy and JDK in your PATH.

see also: https://code.google.com/p/bpipe/wiki/DevelopmentSetup

### PREREQUISITES

* Java JDK (http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)
* groovy (http://groovy.codehaus.org/)
* git (http://git-scm.com/)

On MAC OSX you can install and maintain different versions of groovy with GVM, the Groovy enViroment Manager (http://gvmtool.net/)

### COMPILE SOURCE CODE

1. CLONE REPORSITORY: git clone https://code.google.com/p/bpipe/
2. cd bpipe
3. ./gradlew dist

A Bpipe distribution will be created in the build directory, however you can run Bpipe directly from the distribution directory by typing:

./bin/bpipe

### RUNNING TESTS

If you do make some changes you'll probably want (or at least, should want) to check if you broke some existing functionality. To do this, you can run the regression tests which live in the tests directory of the source distribution. To run the tests, change to the tests directory and just execute the "run.sh" command:

cd tests
./run.sh

The tests will take about 5 minutes to run. Please note that one test depends on having R installed, so if you do not have that you can expect a failure in that test.

