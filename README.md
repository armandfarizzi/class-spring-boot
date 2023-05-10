# class-spring-boot

### Entities

- Employee

employee have only one department whose associated with `department_id`, making department have many employee

| Column Name   | Type                      |
|---------------|---------------------------|
| id            | String [PrimaryKey\|UUID] |
| email         | String                    |
| name          | String                    |
| role          | String [Enumerated]       |
| department_id | String [ForeignKey\|UUID] |

- Department

| Column Name   | Type                      |
|---------------|---------------------------|
| id            | String [PrimaryKey\|UUID] |
| email         | String                    |
| name          | String                    |
| role          | String [Enumerated]       |
| department_id | String [ForeignKey\|UUID] |

TODO
- [X] use `java 17` and `Spring Boot 3.0`
- [X] use `Maven`
- [X] use `Spring Data JPA`
- [X] min 4 API
- [X] Contain 2 entity table
- [X] `snake_case` format
- [X] custom error handler
- [X] validation for API request
- [X] write unit test
- [X] pass compilation
- [X] use swagger
