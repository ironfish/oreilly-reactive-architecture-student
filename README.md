## Oreilly Reactive Architecture Course

---

### Introduction

This document describes how to setup:

- The development environment
- The case study project

We recommend using the following tools:

- Eclipse or IntelliJ
- sbt build tool

---

## Prerequisites

---

### Required Knowledge and Software

This course is best suited for individuals that have knowledge of Java. Also, we need access to the internet and a computer with the following software installed:

- Unix compatible shell
- JVM 1.7 or higher
- Scala 2.11 or higher
- Sbt 0.13.8 or higher

---

### Unix Compatible Shell

If you are running OSX, then you are on a **nix** system already. Otherwise install a Unix compatible shell like [Cygwin](https://www.cygwin.com/).

---

### JVM 1.8 or Higher

If you are running OSX and a [Homebrew Cask](https://github.com/caskroom/homebrew-cask) user, from a terminal run:

```bash
$ brew cask install java
```

Otherwise follow the [setup instructions](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) to download and install. Once the installation is complete, very the installation by running the following command in a terminal session:

```bash
$ java -version
java version "1.8.0_112"
Java(TM) SE Runtime Environment (build 1.8.0_112-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.112-b16, mixed mode)
```

---

### Scala 2.11 or Higher

If you are running OSX and a [Homebrew](http://brew.sh/) user, from a terminal run:

```bash
$ brew install scala
```

Otherwise follow the [setup instruction](http://www.scala-lang.org/documentation/) to download and install. Once the installation is complete, verify the installation by running the following command in a terminal session:

```bash
$ scala -version
Scala code runner version 2.11.8 -- Copyright 2002-2013, LAMP/EPFL
```

---

### Sbt 0.13.13 or Higher

If you are running OSX and a [Homebrew](http://brew.sh/) user, from a terminal run:

```bash
$ brew install sbt
```

Otherwise follow the [setup instruction](http://www.scala-sbt.org/0.13/docs/index.html) to download and install. Once the installation is complete, verify the installation by running the following command in a terminal session:

```bash
$ sbt -version
sbt launcher 0.13.13
```

---

## Simple Build Tool

---

### Make Yourself Familiar with Sbt

- Read the first chapters of the [Getting Started Guide](http://www.scala-sbt.org/release/tutorial/index.html)
- Starting `sbt` takes you to a **interactive session**
- Take a look at `build.sbt` and the other `.sbt` files for Coffee House
- Change directory to the `fast-track-akka-java` directory and start `sbt` as follows:

```scala
$ sbt
[run the man command] base >
```

---

### man

The `man` command, short for manual, displays the setup instructions (what you are reading now) for the courseware. To view the instructions for the current exercise, use the `e` option. If you are using an IDE, you can also open up the setup instructions (`README.md`) file or the current exercises instructions (`src/test/resources/README.md`) file in your workspace.

```scala
// display the setup instructions
[run the man command] base > man

// display the instructions for the current exercise
[run the man command] base > man e
```

---

### run

As part of each exercise, we use the `run` command to bootstrap the main class `CoffeeHouseApp`. This command starts the application for the **current** exercise that we interact with and verify our solution.

```scala
[run the man command] [000] Initial_state > run
```

---

### next/prev

`next` and `prev` are `sbt` commands that allows us to navigate the courseware:

```scala
// move to the next exercise
[run the man command] base > next
[info] Set current project to exercise_000_Initial_state (in build file:/...)
[run the man command] [000] Initial_state >

// move to the previous exercise
[run the man command] [000] Initial_state >
[info] Set current project to base (in build file:/...)
[run the man command] base >
```

---

### clean

To clean your current exercise, use the `clean` command from your `sbt` session. Clean deletes all generated files in the `target` directory.

```scala
[run the man command] [000] Initial_state > clean
```

---

### compile

To compile your current exercise, use the `compile` command from your `sbt` session. This command compiles the source in the `src/main/scala` directory.

```scala
[run the man command] [000] Initial_state > compile
```

---

### reload

To reload `sbt`, use the `reload` command from your `sbt` session. This command reloads the build definitions, `build.sbt`, `project/.scala` and `project/.sbt` files. Reloading is a **requirement** if you change the build definition files.

```scala
[run the man command] [000] Initial_state > reload
```

---

### test

To test your current exercise, use the `test` command from your `sbt` session. Test compiles and runs all tests for the current exercise. Automated tests are your safeguard and validate whether or not you have completed the exercise successfully and are ready to move on.

```scala
[run the man command] [000] Initial_state > test
```

---

## Eclipse

---

### Install the Eclipse IDE

Follow these instructions if you want to use Eclipse:

- You can download and install the latest version from [Eclipse IDE](https://eclipse.org/downloads/) for your platform
- In Eclipse import the `coffee-house` project

---

## IntelliJ

---

### Install the IntelliJ IDEA IDE

Follow these instructions if you want to use IntelliJ IDEA:

- Download and install the latest version of [IntelliJ 2016.3](https://www.jetbrains.com/idea/download/) for your platform
- In Intellij, import the `coffee-house` project
---

## Case Study

---

### Coffee House

Welcome to the Akka Coffee House where we work through a series of exercises organized by topic as laid out in the Fast Track to Akka with java slide deck and experience:

- Yummy caffeinated concoctions like `Akkaccino`, `MochaPlay`, and `CaffeJava`
- Guests becoming **caffeinated**, and waiters can getting **frustrated**
- Barista's becoming **bottlenecks**

Our mission is to keep the Akka Coffee House healthy, so make sure you have the deck handy as it is a useful reference for guidance.

---

### Exercise Outline

0. Exercise 0 > Initial State
1. Exercise 1 > Implement Actor
2. Exercise 2 > Top Level Actor
3. Exercise 3 > Message Actor
4. Exercise 4 > Use Sender
5. Exercise 5 > Child Actors
6. Exercise 6 > Actor State
7. Exercise 7 > Use Scheduler
8. Exercise 8 > Busy Actor
9. Exercise 9 > Stop Actor
10. Exercise 10 > Lifecycle Monitoring
11. Exercise 11 > Faulty Guest
12. Exercise 12 > Custom Supervision
13. Exercise 13 > Faulty Waiter
14. Exercise 14 > Self Healing
15. Exercise 15 > Detect Bottleneck
16. Exercise 16 > Use Router
17. Exercise 17 > Config Dispatcher
18. Exercise 18 > Modify Behavior
19. Exercise 19 > Remoting