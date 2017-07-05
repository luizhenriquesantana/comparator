# Base64-comparator

That's a simple project for WAES Selective Process.
Its purpose is save and comparing base64 through REST requests with json.

### Requiremtns:
	
The assignment

• Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints

	o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
• The provided data needs to be diff-ed and the results shall be available on a third end point
	o <host>/v1/diff/<ID>
• The results shall provide the following info in JSON format
	o If equal return that
	o If not of equal size just return that
	o If of same size provide insight in where the diffs are, actual diffs are not needed.
		§ So mainly offsets + length in the data
• Make assumptions in the implementation explicit, choices are good but need to be communicated

## Technologies used

* I've decided for using a SpringBoot over eclipse solution for this assignment because it provides a complete working set with maven, H2 database and Tomcat.

## Installation

This project is java-based. So It requires Jdk 1.8 (or later) and Maven 3.5 (or later)  to run.

```sh
$ cd base64
$ mvn package
$ java -jar target/comparator-1.0.jar 
```

## Running tests
After changes you can run tests using Maven command:
```sh
$ cd base64
$ mvn test
```

## Rest API

 As required, this API has three endpoints and they are according the assignment. 
 I recommend to use SOAP-UI or Postman for testing.