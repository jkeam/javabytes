Javabytes
=====================================
[![Build Status](https://travis-ci.org/jkeam/javabytes.svg?branch=master)](https://travis-ci.org/jkeam/javabytes)

Welcome to Javabytes, a disassembler for your java code. Inspired by a project by Matt Godbolt called [GCC Explorer](https://github.com/mattgodbolt/gcc-explorer).
Enter some arbitrary java code and this application will do a few things: 

1. Generate a random class to hold your code.
2. Put all of the code you entered into a public static void test() method.

This is done solely out of convenience so that you don't have to write boilerplate code everytime.

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
