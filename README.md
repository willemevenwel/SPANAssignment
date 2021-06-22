
# SPAN Assignment

## Problem Statement & Instructions
[Skip to my Approach](#my-approach)

Read the problem statement, code a working solution (valid input and output will be provided)
and supporting tests using a language of your choice. Be prepared to explain your solution
during a review.

### The Problem

We want you to create a production ready, maintainable, testable command-line application that
will calculate the ranking table for a league.

### Input/output

The input and output will be text. Either using stdin/stdout or taking filenames on the command
line is fine.

The input contains results of games, one per line. See “Sample input” for details. 
The output should be ordered from most to least points, following the format specified in
“Expected output”.

You can expect that the input will be well-formed. There is no need to add special handling for
malformed input files.

### The rules

In this league, a draw (tie) is worth 1 point and a win is worth 3 points. A loss is worth 0 points.
If two or more teams have the same number of points, they should have the same rank and be
printed in alphabetical order (as in the tie for 3rd place in the sample data).

### Guidelines

This should be implemented in a language with which you are familiar. We would prefer that
you use javascript, coffeescript, ruby, python, java, or C, if you are comfortable doing so. If
none of these is comfortable, please choose a language that is both comfortable for you and
suited to the task.

If you use other libraries installed by a common package manager (rubygems/bundler, npm, pip), it is not necessary to commit the installed packages.
We write automated tests and we would like you to do so as well.

If there are any complicated setup steps necessary to run your solution, please document them.

### Platform support
This will be run in a unix-ish environment (OS X). If you choose to use a compiled language,
please keep this in mind. Please use platform-agnostic constructs where possible (line-endings
and file-path-separators are two problematic areas).

## Sample input:
```
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0
```

## Expected output:
```
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts
```

#my-approach
## My Approach

The first thing I did was decide on the tools I was going to use. Java 1.8 and IntelliJ as this is what I'm most fluent in.
The next thing I did was start up a hello world project and getting that initialized with git. And pushing it to GitHub.

The next thing I did, which is something I usually do next, was to configure a logging library. Using an older version of log4j v1.2.17 
(https://howtodoinjava.com/log4j/how-to-configure-log4j-using-properties-file/) Because I wasted so much time trying to get the newest version to work, 
and it just didnt. I'm thinking its because its not REALLY compatible with JDK1.8 which is still my preferred JDK version. 

The next steps was designing my framework for the application. I decided on implementing a watcher on a directory, which will listen for files dropped.
These will be the sample files (I call them match files, like football match) and process them.

In order to get the statistics, I thought to use an in memory database. I then also decided to implement a connection pooling data access layer framework.
Which can assist in executing various queries on the data.
 
## Tech Stack

Application is a IntelliJ Community edition Gradle Java Project.

Using Java 1.8

Libs used:
```
    implementation group: 'log4j', name: 'log4j', version: '1.2.17'
    implementation group: 'org.jdbi', name: 'jdbi', version: '2.78'
    implementation group: 'com.h2database', name: 'h2', version: '1.4.200'
```


      
## Running Tests

To run tests, run the following command

```bash
  npm run test
```

## RoapMap

Want to implement a Thread pool, so that once a file comes in it gets added to the pool to get processed, instead of immediately processed.

  
## Acknowledgements

 - [readme.so](https://readme.so/)

  
## Authors

- [@willemevenwel](https://github.com/willemevenwel/)

  
