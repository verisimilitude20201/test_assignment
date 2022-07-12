# Coding Challenge

## Installation Notes
1. Tested in MySQL. Please Use below SQL commands to set up a new database to test the app

```
CREATE DATABASE employee;
CREATE USER 'emp_assignment'@'%' IDENTIFIED BY 'Imissmyfather20191!'; 
GRANT ALL ON employee.* TO 'emp_assignment'@'%'; 
```
2. Install Gradle, build and deploy the application. 

3. Need to change the MySQL connection string in application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/employee
spring.datasource.username=emp_assignment
spring.datasource.password=Imissmyfather20191!
```

3. The application has Swagger support to make it more easy to test the APIs. Access Swagger UI at http://dummy.restapiexample.com:8080/swagger-ui/index.html




## Assumptions
1. Renamed the endpoints a bit to make it more RESTful. All endpoints can be accessed from the Swagger URL

2. id is the primary key and it's an integer field. The problem statement had us input the id while creating the employee. I made it auto_increment

    
## Note
I am not used to TDD/unit test cases writing. Opted to skip it. It's a skill, I'd like to learn.



### External endpoints from base url
#### This section will outline all available endpoints and their request and response models from https://dummy.restapiexample.com
/api/v1/employee/?page=0 (200 OK)

    request:
        method: GET
        parameters: n/a
        full route: https://dummy.restapiexample.com/api/v1/employees
    response:
        {
            "status": "success",
            "data": [
                {
                "id": "1",
                "employee_name": "Tiger Nixon",
                "employee_salary": "320800",
                "employee_age": "61",
                "profile_image": ""
                },
                ....
            ]
        }

/api/v1/employee/{id}

    request:
        method: GET
        parameters: 
            id (String)
        full route: https://dummy.restapiexample.com/api/v1/employee/{id}
    response: 
        {
            "status": "success",
            "data": {
                "id": "1",
                "employee_name": "Foo Bar",
                "employee_salary": "320800",
                "employee_age": "61",
                "profile_image": ""
            }
        }

/api/v1/employee/create

    request:
        method: POST
        parameters: 
            name (String),
            salary (String),
            age (String)
        full route: https://dummy.restapiexample.com/api/v1/employee/create
    response:
        {
            "status": "success",
            "data": {
                "name": "test",
                "salary": "123",
                "age": "23",
                "id": 25
            }
        }

/api/v1/employee/delete/{id}

    request:
        method: DELETE
        parameters:
            id (String)
        full route: https://dummy.restapiexample.com//api/v1/employee/delete/{id}
    response:
        {
            "status": "success",
            "message": "successfully! deleted Record"
        }