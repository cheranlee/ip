# DuckTask

Welcome to DuckTask, a simple and intuitive way to store your tasks.

## Running the JAR File 

[add stuff here!]

## Setting up in IntelliJ

Prerequisites: JDK 21.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into IntelliJ as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 21** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. Run the code using `./gradlew run` in the Command Line Interface. The JavaFX Interface will open.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running Tests

The project comes with a set of test scripts for different operating systems:

- **MacOS/Linux**: Run the shell script
  ```bash
  cd text-ui-test
  ./runtest.sh
  ```

- **Windows**: Run the batch script
  ```batch
  cd text-ui-test
  runtest.bat
  ```

These scripts will compile the source files, run the tests, and compare the output against the expected output.
