Javabytes
=====================================
[![Build Status](https://travis-ci.org/jkeam/javabytes.svg?branch=master)](https://travis-ci.org/jkeam/javabytes)

Welcome to Javabytes, a disassembler for your java code. Inspired by a project by Matt Godbolt called [GCC Explorer](https://github.com/mattgodbolt/gcc-explorer).

Enter some Java and this application will show you the JVM bytecode that is generated.

Currently only supports [OpenJDK8](http://openjdk.java.net/projects/jdk8/).

The demo site can be found here at [javabytes.io](http://javabytes.io/).

## Development

### Prereq
1.  Java 8
2.  Scala 2.11.x
3.  Play 2.7
4.  SBT 1.2.x

  ```
  # create tmp directory
  mkdir -p /tmp/javabytes
  ```

### Setup
1.  Check out this project
2.  Run server

  ```
  sbt run
  ```
