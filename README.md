[![Build Status](https://travis-ci.org/zjx-immersion/api-stencil.svg?branch=master)](https://travis-ci.org/zjx-immersion/api-stencil)

# Springboot API stencil

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:


## Building for production

To Build and Test:

    ./gradlew

To run via gradle:

    ./gradlew bootRun

To optimize the foo client for production, run:

    ./gradlew clean bootRepackage

To ensure everything worked, run:

    java -jar build/libs/*.jar --spring.profiles.active=local

## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `api-stencil`
* Source Code Management
    * Git Repository: `https://github.com/zjx-immersion/api-stencil.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Gradle script / Use Gradle Wrapper / Tasks: `clean test bootRepackage`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

# Auto-Deploy in Heroku 
https://apistencil.herokuapp.com/api-stencil/info 
