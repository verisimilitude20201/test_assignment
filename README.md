# Coding Challenge

## Notes
1. Tested in MySQL. Please Use below SQL commands to set up a new database to test the app

```
CREATE DATABASE employee;
CREATE USER 'emp_assignment'@'%' IDENTIFIED BY 'Imissmyfather20191!'; 
GRANT ALL ON employee.* TO 'emp_assignment'@'%'; 
```

2. Assumptions
    - The GET APIs for /employee & /employee/{searchString} are all paginated. Page size is 5 and page number is from 0 through total_pages - 1. 
    - I've renamed the endpoints a bit to make it more RESTful. 
    
3. The assignment got delayed by almost 2 weeks due to my ill-health. I am not used to TDD and have'nt written unit tests. This is something, I'd like to learn and
practise. The assignment does'nt include TDDs. Did'nt want to spend one more week learning and doing, it would have taken a lot of time.


### External endpoints from base url
#### This section will outline all available endpoints and their request and response models from https://dummy.restapiexample.com
/v1/api/employees (200 OK)

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

/employee/{id}

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

/create

    request:
        method: POST
        parameters: 
            name (String),
            salary (String),
            age (String)
        full route: https://dummy.restapiexample.com/api/v1/create
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

/delete/{id}

    request:
        method: DELETE
        parameters:
            id (String)
        full route: https://dummy.restapiexample.com/api/v1/delete/{id}
    response:
        {
            "status": "success",
            "message": "successfully! deleted Record"
        }