package com.example.rqchallenge.mapper;

import com.example.rqchallenge.entity.Employee;

public class Mapper {
  public static com.example.rqchallenge.model.Employee toEmployeeModel(Employee employee) {
    com.example.rqchallenge.model.Employee employee1 = new com.example.rqchallenge.model.Employee();
    employee1.setId(employee.getId());
    employee1.setName(employee.getName());
    employee1.setAge(employee.getAge());
    employee1.setSalary(employee.getSalary());
    employee1.setProfileImage(employee.getProfileImage());

    return employee1;
  }
}
