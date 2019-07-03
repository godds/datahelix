# Build and run the generator using an IDE

The instructions below explain how to download the generator source code, build it and run it, using a Java IDE.  This is the recommended setup if you would like to contribute to the project yourself.  If you would like to use Docker to build the source code and run the generator, [please follow these alternate instructions](DockerSetup.md).

## Get Code

Clone the repository to your local development folder.

```
git clone https://github.com/finos/datahelix.git
```

## Installation Requirements

* Java version 1.8
* Gradle
* Cucumber
* One of IntelliJ/Eclipse IDE 

### Java

[Download JDK 8 SE](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). 

(*Please note, this has been tested with jdk1.8.0_172 but later versions of JDK 1.8 may still work)*

In Control Panel: edit your environment variables; set `JAVA_HOME=C:\Program Files\Java\jdk1.8.0_172`.  
Add Java binary utilities to your `PATH` (`C:\Program Files\Java\jdk1.8.0_172\bin`).

### Gradle

Download and install Gradle, following the [instructions on their project website](https://docs.gradle.org/current/userguide/installation.html).

### IntelliJ IDE

Get IntelliJ. [EAP](https://www.jetbrains.com/idea/nextversion/) gives you all features of Ultimate (improves framework support and polyglot).

### Eclipse

Alternatively, download and install [Eclipse](https://www.eclipse.org/downloads/). Please note we do not have detailed documentation for using the generator from Eclipse.

### Cucumber

Add **Gherkin** and **Cucumber for Java** plugins (file > settings > plugins if using IntelliJ IDE).

Currently the tests cannot be run from the TestRunner class.

To run a feature file you’ll have to modify the configuration by removing .steps from the end of the Glue field. 

An explanation of the particular syntax used can be found [here](https://github.com/finos/datahelix/blob/master/docs/CucumberSyntax.md).

## First time setup

### IntelliJ

On IntelliJ's splash screen, choose "Open".

Open the repository root directory, `datahelix`.

Right-click the backend Module, `generator`, choose "Open Module Settings".

In "Project": specify a Project SDK (Java 1.8), clicking "New..." if necessary.  
Set Project language level to 8.

Open the "Gradle Projects" Tool Window, and double-click _Tasks > build > build.
Your IDE may do this automatically for you.

Navigate to the `App.java` file (...\datahelix\generator\src\main\java\com\scottlogic\deg\generator\App.java). Right click and debug - *this will fail*.

Now edit the run configuration on the top toolbar created by the initial run. Name the run configuration 'Generate' and under 'Program Arguments' enter the following, replacing the paths with your desired locations:

```
generate --profile-file="<path to an example JSON profile>" --output-path="<path to desired output CSV>"
```

Additionally create another run configuration called GenerateViolating and add the program arguments

```
violate --profile-file="<path to an example JSON profile>" --output-path="<path to desired output folder for generated CSVs>"
```

Run both of these configurations to test that installation is successful.

### Command Line

Build the tool with all its dependencies:

`gradle fatJar`

To generate valid data run the following command from the command line:

`java -jar <path to JAR file> generate [options] --profile-file="<path to profile>" --output-path="<desired output path>"`

* `[path to JAR file]` - the location of `generator.jar`.
* `[options]` - optionally a combination of [options](../../docs/Options/GenerateOptions.md) to configure how the command operates.
* `<path to profile>` - the location of the JSON profile file.
* `<desired output path>` - the location of the generated data.

To generate violating data run the following command from the command line:

`java -jar <path to JAR file> violate [options] --profile-file="<path to profile>" --output-path="<desired output folder>"`

* `[path to JAR file]` - the location of `generator.jar`.
* `[options]` - a combination of any (or none) of [the options documented here](../../docs/Options/ViolateOptions.md) to configure how the command operates.
* `<path to profile>` - the location of the JSON profile file.
* `<desired output folder>` - the location of a folder in which to create generated data files.
