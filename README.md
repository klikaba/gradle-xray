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

### Upload XML Report

To be able to upload XML report you must know two things: Test execution key and file path. 

File : `build.gradle`

```groovy

xrayCredentials {
    clientId = "xxxxxxxxxxxx"
    clientSecret = "xxxxxxxxxx"
}

task uploadTest(type: ba.klika.tasks.UploadXmlReportTask) {
    testExecution = "TEST-123"
    filePath = "$buildDir/reports/tests/{testName}/testng-results.xml".toString()
}

```

In order to upload XML report after test automatically just add `finalizedBy` at the end of the test referencing to the upload task like this:

```groovy

task regressionTest(type: Test) {
    useTestNG() {
        useDefaultListeners = false
        suites "src/test/resources/suites/regression_test.xml"
    }
    finalizedBy uploadRegressionTest
}

```
