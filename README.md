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
2.  Scala 1.10
3.  Play 2.3.1
4.  SBT 0.13

  ```
  brew install typesafe-activator
  # also create tmp directory
  mkdir -p /tmp/javabytes
  ```

### Starting the Server
1.  Config app_secret and replace value in application.conf.  [Read here for more information on how to do that.](https://www.playframework.com/documentation/2.5.x/ApplicationSecret).

2.  Start activator shell

  ```
  activator
  ```
3.  Run app

  ```
  run
  ```
