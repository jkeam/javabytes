Javabytes
=====================================
[![Build Status](https://travis-ci.org/jkeam/javabytes.svg?branch=master)](https://travis-ci.org/jkeam/javabytes)

Welcome to Javabytes, a disassembler for your java code. Inspired by a project by Matt Godbolt called [GCC Explorer](https://github.com/mattgodbolt/gcc-explorer).

Enter some Java and this application will show you the JVM bytecode that is generated.

Currently only supports [OpenJDK8](http://openjdk.java.net/projects/jdk8/)

The demo site can be found [here](http://javabytes.io/).

## Development

### Prereq
1.  Java 8
2.  Play
`brew install typesafe-activator`

### Starting the Server
1.  Config app_secret and replace value in application.conf
2.  Start activator shell
`activator`
3.  Run app
`run`

or to debug

`activator -jvm-debug 9999 run`
