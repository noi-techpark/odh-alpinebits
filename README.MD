![Logo of the project](https://raw.githubusercontent.com/idm-suedtirol/odh-alpinebits/master/alpine_bits_rgb.png)

# AlpineBits Server

[AlpineBits](https://www.alpinebits.org/) is an open standard to exchange
touristic data.

This repository provides a library to implement AlpineBits server functionality.
Use it to build your own standalone AlpineBits server, or integrate parts
of it into your existing code base.

## Table of Contents

- [Getting started](#getting-started)
- [Running the tests](#running-the-tests)
- [Deployment](#deployment)
- [Information](#information)

## Getting started

These instructions will get you a copy of the project up and running
on your local machine for development and testing purposes. Take a look
at the [Deployment](#deployment) section for notes on how to deploy
the project on a live system.

### Prerequisites

To build the project, the following prerequisites must be met:

- Java JDK 1.8 or higher (e.g. [OpenJDK](https://openjdk.java.net/))
- [Maven](https://maven.apache.org/) 3.x

### Installing

Get a copy of the repository, e.g. by cloning it from the following location:

```
git clone https://github.com/idm-suedtirol/odh-alpinebits.git
```

Change directory:

``` 
cd odh-alpinebits/
```

Build the project:

```
mvn clean install
```

### Examples

The `examples` folder contains modules with pre-configured AlpineBits server
instances, that are easy to get up and running. The main point of the examples
is to demonstrate the usage of the library to create a functioning AlpineBits
server.

#### Example: Housekeeping actions

The `housekeeping` example provides an AlpineBits server instance supporting
the so called Housekeeping actions.

Change to the `housekeeping` directory (assuming you are in the projects root
folder):

```
cd examples/housekeeping
```

Run the following maven command:

```
mvn cargo:run
```

This command starts a local Tomcat 8 instance using
[Cargo](https://codehaus-cargo.github.io/cargo/Maven2+plugin.html) and deploys
the example war file, generated during the installation (see [Installing](#installing)).

The deployment contains an AlpineBits server instance supporting the
Housekeeping actions and a simple website to facilitate the communication with
the server.

After Tomcat started, Open the website at
[http://localhost:8080/examples-housekeeping/index.html](http://localhost:8080/examples-housekeeping/index.html).

The website provides a set of links and an output area. If you click on any of
the links, the website will issue a request against the deployed AlpineBits
server instance and display the results.

## Running the tests

The code in this repository provides unit and integration tests.

The unit tests can be run with the following command:

```
mvn clean verify
```

To run the unit and integration tests, use the following command, activating the
maven `it` profile:

```
mvn clean verify -P it
```

You can also integrate [sonarqube](https://www.sonarqube.org/) to check the code
quality. Run the following command:

```
mvn clean verify sonar:sonar -P it
```
 
## Deployment

The project provides a servlet as entry point to the AlpineBits server. If you want,
to use it, the following additional prerequisites must be met:

- Servlet Container 3.x (e.g. [Tomcat 8](https://tomcat.apache.org/download-80.cgi))

Using the provided examples as template (see [Examples](#examples)), you should
be able to configure your AlpineBits server and to deploy the result as a war
onto a servlet container. 

## Information

### Support

For support, please contact [Christian Gapp](https://github.com/gappc) or
[IDM Südtirol - Alto Adige](https://www.idm-suedtirol.com/en/home.html).

### Contributing

If you'd like to contribute, please fork the repository and use a feature
branch. Pull requests are warmly welcome.

### Versioning

This project uses [SemVer](https://semver.org/) for versioning. For the versions available,
see the [tags on this repository](https://github.com/idm-suedtirol/odh-alpinebits/tags). 

### License

The code in this project is licensed under the Mozilla Public License 2.0 license.
See the LICENSE file for more information.

### Authors

* **Christian Gapp** - *Initial work* - [gappc](https://github.com/gappc)

### Acknowledgements

- [IDM Südtirol - Alto Adige](https://www.idm-suedtirol.com/en/home.html)