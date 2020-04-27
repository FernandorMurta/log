# LOG

LOG is a Spring Boot Application Written in java

## Tools
- Java 8
- Spring Boot
- Spring Data
- JPA
- Hibernate
- PostgreSQL (to de database)
- Swagger Ui
- Maven Checkstyle Plugin
- Lombok
- Maven
- QueryDSL


## Installation

Create a database (on PostgreSQL) - Suggested name: loginfo
```bash
mvn clean package -Ddatabase-url=yourconnectionurl -Ddatabase-user=youruserofdatabase -Ddatabase-password=yourpassword
```

You can execute the build without the arguments, but the default values of each properties are:
 - database-url = jdbc:postgresql://localhost:5432/loginfo
 - database-user = postgres
 - database-password = root


After the package was built use

```bash
java -jar target/log-0.0.1-SNAPSHOT.jar
```


## Usage

To see the List of Services go to

[Swagger](http://localhost:8080/api/swagger-ui.html)

[Swagger api-doc](http://localhost:8080/api/v2/api-docs)

## Examples

Where is a link to download postman collection with some examples

[Postman Collection](https://www.getpostman.com/collections/0ea5f01c75f4c8351c80)


## Usage notes

Remember the first thing to do is create a new Database(Only database, the tables are created in the startup of the application)


* All link (except postman collection) redirect you to the a page when the application goes up!
