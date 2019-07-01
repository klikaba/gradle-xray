# Xray Gradle Plugin
Gradle plugin for uploading TestNG reports to XRay

## Summary

This plugin allows you to upload TestNG XML reports to Xray on Jira.

## How to install

File : `build.gradle`

```groovy
plugins {
  id "ba.klika.xray" version "1.0"
}
```

Or using legacy plugin application:

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.ba.klika:xray:1.0"
  }
}

apply plugin: 'ba.klika.xray'
```

## How to use

Generate Xray API credentials

File : `build.gradle`

```groovy
xrayCredentials {
    clientId = "xxxxxxxxxxxxx"
    clientSecret = "xxxxxxxxxxxx"
}
```

### Upload XML Task

To be able to upload XML report you must know two things: Test Execution Key and file path. 

In order to upload XML report after test you need to run Upload task after tests are executed (finalizedBy). Just add:

File : `build.gradle`

```groovy
task testA(type: Test) {
    useTestNG() {
        useDefaultListeners = false
        suites "suitename.xml"
    }
    finalizedBy uploadTestA
}
```

This will upload the XML report of `testA`.

### Example 1

Simple script to upload report to Xray after tests:

```groovy

xrayCredentials {
    clientId = "xxxxxxxxxxxx"
    clientSecret = "xxxxxxxxxx"
}

task uploadTestExecutionA(type: ba.klika.tasks.UploadXmlReportTask) {
    testExecution = "TestExecutionA"
    filePath = "$buildDir/reports/tests/TestExecutionA/testng-results.xml".toString()
}

task testExecutionA(type: Test) {
    useTestNG() {
        useDefaultListeners = false
        suites "src/test/resources/suites/testExecutionA.xml"
    }
    finalizedBy uploadTestExecutionA
}
```
