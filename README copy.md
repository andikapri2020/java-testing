# Project Title

A paragraph of project description.

## Table of Contents

- [Project Title](#project-title)
  - [Table of Contents](#table-of-contents)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Running the Tests](#running-the-tests)
    - [Code Smell and Coverage Check with Sonarqube](#code-smell-and-coverage-check-with-sonarqube)
  - [Deployment](#deployment)
  - [Contributing](#contributing)
    - [Git Flow](#git-flow)
    - [Commit Message](#commit-message)
    - [Unit Tests](#unit-tests)
    - [GraalVM Metadata](#graalvm-metadata)
  - [Versioning](#versioning)
  - [License](#license)

## Getting Started

These instructions will give you a copy of the project up and running on your local machine for development and testing purposes. See [deployment](#deployment) for notes on deploying the project on a live system.

### Prerequisites

Requirements for the software and other tools to build, test and push

If you are Non-Developers, you only need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Git](https://git-scm.com/downloads) (Optional, if you want to clone repo with git, or you can just download the source code)
- [Docker](https://www.docker.com/get-started/) (Optional, if you want to deploy the application in isolated environment)

If you are developers, you will need:

- [GraalVM for JDK 17](https://www.graalvm.org/latest/docs/getting-started/)
- [Git](https://git-scm.com/downloads)
- [WSL](https://ubuntu.com/desktop/wsl) (recommended for build and test)
- [Docker](https://www.docker.com/get-started/)
- [Coding style format](#prerequisites) (Ask other engineer or your supervisor)

### Installation

To get started with this project on development environment, follow these steps:

1. Clone this repository to your local machine:

    ```bash
    git clone <url>
    ```

2. Change directory to the repository

    ```bash
    cd <repository>
    ```

3. Switch branch to develop
  
    ```bash
    git checkout develop
    ```

4. Start the service
  
    ```bash
    ./mvnw spring-boot:run
    ```

    If you get an error like this "./mvnw: /bin/sh^M: bad interpreter: No such file or directory", run this and then run step 4 again. If not, you can continue to step 5.

      ```bash
      sed -i 's/\r$//' mvnw
      ```

5. Now you should be able to use the service and access the API documentation by opening the following link
      <http://localhost:8080/swagger-ui/index.html>

If there are steps you don't understand or an error occurs, feel free to ask the backend engineering team.

## Running the Tests

To run the automated tests for this project, use the following command:

```bash
./mvnw clean test
```

If there are jacoco defined in the pom.xml, it will generate coverage report, you can access it on `./target/site/jacoco/index.html`

### Code Smell and Coverage Check with Sonarqube

To check if there are any code smell, you can host a sonarqube locally and push the project to it:

1. Run the sonarqube

    ```bash
    docker run -d --name sonarqube --rm -p 9000:9000 sonarqube:latest
    ```

2. Push the project to sonarqube

    ```bash
    ./mvnw clean verify sonar:sonar "-Dsonar.host.url=http://127.0.0.1:9000" -Dsonar.projectKey="<project_name>"
    ```  

3. Open sonarqube dashboard <http://localhost:9000> and navigate to your pushed project.

## Deployment

To deploy the service on Docker, you can use the docker compose available.

```bash
docker compose -f <compose-file> up
```

If you want to switch between JVM (jar) and GraalVM (native binary), you can change the Dockerfile inside the compose file to `<dockerfile-name-for-JVM>` to use JVM or `<dockerfile-name-for-graalvm>` to use GraalVM.

Or if you want to build the jar locally, you can do:

1. Build the jar

    ```bash
    ./mvnw clean package
    ```

2. Run the jar

    ```bash
    java -jar ./target/<app>.jar
    ```

Or if you want to build the native binary locally (need GraalVM), you can do:

1. Build the native binary

    ```bash
    ./mvnw clean package -Pnative
    ```

2. run the native binary

    ```bash
    ./target/<app>
    ```

If you get permission denied when running the step 2, you have to add executable permission with `chmod +x ./target/<app>` then run step 2 again.

If you choose to build native binary (docker or locally), please note that the build will require a large amount of memory and quite a long time.

## Contributing

To contribute in this project, you have to:

1. **Bug reports and feature requests**
     - If you find a bug, please report it to the backend engineer or create the issue on OpenProject.
     - When submitting a bug report, please include clear steps to reproduce the issue and any relevant error messages.
     - If you have suggestion for a new feature, feel free to discuss it with project manager.
2. **Merge requests**
    - If you want to contribute code changes, bug fixes, or improvements, please do so via merge requests.
    - Create the card/issue on OpenProject to get the card/issue number
    - Clone the repository and create new branch for your changes with

      ```bash
      git checkout -b <type>/<issue-title>-#<issue-number>
      ```

      For more information about branch names, please look at [Git Flow section](#git-flow). Ensure that you are **branching from up-to-date develop branch**.
    - Make sure your code adheres to the project's [coding style guidelines](#prerequisites).
    - Include [unit tests](#unit-tests) for any new code or bug fixes and ensure the **unit tests and service work properly both locally and on docker container after changes**.
    - Also include the API Documentation if needed for any new code or bug fixes.
    - **Write clear and concise commit messages** that describe your changes with

      ```bash
      git commit -m "type: summary
          
      description"
      ```

      For more information about commit message, please look at [Commit Message section](#commit-message) to know how we usually write our commit message.
    - Push your changes to the remote branch with

      ```bash
      git push origin feat/issue-or-card-name-issueNumber
      ```

    - Ensure that **your branch are up to date and no conflict with develop branch** before merge request.
    - Create a new **merge request to develop branch** and request review to peer and set assignee to supervisor.

### Git Flow

In general, our git flow are like these:

- There are 4 branches (master, release, develop, and feature branch for development feature or enhancement)
- Every Feature will be branched from the develop branch and hotfix will be branched from release branch.
- Merge Request will be created from the feature branch to the develop branch for testing by QA & Engineer
- For hotfixes, merge request will be created from hotfix branch to the release and develop branches simultaneously.
- Feature branch names should follow our modified [semantic branch names](https://gist.github.com/seunggabi/87f8c722d35cd07deb3f649d45a31082) guideline with format `<type>/<issue-name>-#<issue-number>`. The `<type>` field will be explained in [Commit Message section](#commit-message) and the issue number can be obtained via OpenProject.

### Commit Message

Usually, we write commit message following [semantic commit message](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716) guideline with addition of description. So the message structure will be

```bash
<type>: <summary>

<description>
```

The `<type>` field is an enumerated type with values:

- `chore`: The commit includes a technical or preventative maintenance task that is necessary for managing the product or the repository, but it is not tied to any specific feature or user story. For example, releasing the product can be considered a chore. Regenerating generated code that must be included in the repository could be a chore.
- `docs`: The commit adds, updates, or revises documentation that is stored in the repository.
- `feat`: The commit implements a new feature for the application.
- `fix`: The commit fixes a defect in the application.
- `refactor`: The commit refactors existing code in the product, but does not alter or change existing behavior in the product.
- `remove`: The commit removes a feature from the product. Typically features are deprecated first for a period of time before being removed. Removing a feature from the product may be considered a breaking change that will require a major version number increment.
- `revert`: The commit reverts one or more commits that were previously included in the product, but were accidentally merged or serious issues were discovered that required their removal from the `release` branch.
- `test`: The commit adds, updates, or revises the unit tests that included in the repository.
- `style`: The commit changes the code formatting without changing production code.

The `<type>` field can be used with automated tools to automatically generate release notes or update a change log for the product release. If you keep a change log following the [Keep a Changelog](https://keepachangelog.com/en/1.0.0/) style, you could map the `<type>` fields to change log headings like this:

- `feat` → **Added**
- `change` → **Changed**
- `deprecate` → **Deprecated**
- `remove` → **Removed**
- `fix` → **Fixed**

### Unit Tests

Usually we create unit tests for layer controllers and services, but it does not rule out the possibility to tests on other layers to ensure the service functions properly.

Here is an example how we usually create a unit test.

  ```java
  @SpringbootTest
  @DisplayName("Layer Name Test Suite")
  class LayerNameTests {


      @Nested
      @DisplayName("Name of System Under Test Test Suite")
      class SystemUnderTheTest {

        @BeforeEach
        static void setUp() {
            // Set up anything that will be used for most of the test in this class
        }

        @AfterEach
        static void tearDown() {
            // Finish anything that needs to happen after each test
        }
        
        @Test
        @WithMockUser(authorities = { "Role" })
        @DisplayName("What test it does to the system under test")
        void testDoSomethingToSUT() throws Exception {
            // Mock anything that need to be mocked
            // assert that system under test work properly
        }

        @ParameterizedTest
        @ValueSource(strings = {
          "value1"
          "value2",
        })
        @NullAndEmptySource
        @DisplayName("What test it does to the system under test with different input")
        void testDoSomethingToSUTWithDifferentInput(String value) throws Exception {
            // Mock anything that need to be mocked
            // assert that system under test work properly
        }

      }
  }
  ```

  Usually we will make a set up and tear down to achieve DRY (Don't Repeat Yourself). When we need to test with an input, we will use annotation `@Test` and test the system under test (SUT) then when we need to test with multiple input but the pattern of output will be the same, we use `@ParameterizedTest` so we achieve DRY.
  
  We define display name on each class and test to help us understand the tests being performed more clearly. Also, we always start the test function name with `test`. If we need to test with authentication, we  define `@WithMockUser` annotation and specify an authority such as Admin so we can simulate the SUT with fake user. For more information about testing on this project, feel free to ask the Backend engineer.

### GraalVM Metadata

If you make any code changes, you must generate GraalVM metadata to convert java application into a native executable. Each java feature that requires metadata has a corresponding JSON file named `<feature>-config.json`. The JSON file consists of entries that tell native image the elements to include.

To get the metadata and create a native executable, you will need to:

1. Create jar file
  
    ```bash
    ./mvnw clean package
    ```

2. Run the jar file with an agent to generate metadata

    ```bash
    java -agentlib:native-image-agent=config-output-dir="./src/main/resources/META-INF/native-image" -jar ./target/<app>.jar
    ```

    The agent will monitor every process that occurs within the application and write every metadata it finds to the output directory. So, you have to simulate every possible scenario that could happen on the application.

3. Generate the native executable.

    ```bash
    ./mvnw clean package -Pnative
    ```

Additional notes:

- Sometimes the libraries used are different for windows and linux, so it is recommended to use WSL to get metadata with linux environment.
- Step 2 will rewrite the previous metadata, so you will need to run every possible scenario not only for the new code changes, but the entire application.
- There is a way to simulate new code changes only. You will have to change the output directory then simulate the new scenario. After that, compare the new metadata and add/remove differences to the old metadata.

## Versioning

We use [Semantic Versioning](http://semver.org/) for versioning. For the versions available, see the [tags on this
repository](<url>).

## License

This project is licensed under the [ITSec Asia](LICENSE.md) Proprietary License and Confidential - see the [LICENSE.md](LICENSE.md) file for details.
