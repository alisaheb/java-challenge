### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years

#### What I did with this project
- I added model class for incoming request and response the request because don't want to expose the entity to the client.
- For global exception handling add @ControllerAdvice into the project.
- Add enums to avoid redundancy.
- Add incoming request validator for validate the incoming request.
- For validating I use java bean validation.
- Add unite test for every method.
- For testing I used Spock framework and Groovy because I found it is very handy tools for UT.
- Fix the code bug.
- Configure the Swagger and some metadata for Swagger documentation.

#### What if I had time I wish to do with this project.
- Add spring security with JWT token.
- Add pagination on employees endpoint.
- Add more test case.
- Add a Message broker

#### My java development journey
- I started writing code using java back in 2012 for my university project.
- I have been working using spring boot more than 4 years.
- Using Spring Boot I developed several projects which now on production and can serve millions users.
- I have experience on Spring Batch more than two years.
- Using Spring Batch I developed several projects.
