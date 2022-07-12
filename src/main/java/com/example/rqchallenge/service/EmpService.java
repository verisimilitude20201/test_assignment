package com.example.rqchallenge.service;

import com.example.rqchallenge.constants.Status;
import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.mapper.Mapper;
import com.example.rqchallenge.model.response.EmpResponse;
import com.example.rqchallenge.model.response.EmpResponseSingle;
import com.example.rqchallenge.model.response.EmpResponseStat;
import com.example.rqchallenge.repository.EmployeeRepository;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

import org.slf4j.Logger;

@Service
@Transactional
public class EmpService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private SessionFactory sessionFactory;


  private Logger logger = LoggerFactory.getLogger(EmpService.class);

  public EmpResponseSingle saveAndReturnNewEmp(Map<String, String> employeeInput) {
    EmpResponseSingle empResponseSingle = new EmpResponseSingle();
    try {
      Employee employee = new Employee();
      employee.setAge(Integer.parseInt(employeeInput.get("age")));
      employee.setSalary(Integer.parseInt(employeeInput.get("salary")));
      employee.setName(employeeInput.get("name"));
      employee.setProfileImage(employeeInput.get("profile_image"));
      employeeRepository.save(employee);
      empResponseSingle.setSingleEmp(Mapper.toEmployeeModel(employee));
      empResponseSingle.setStatus(Status.SUCCESS.toString());
    } catch (Exception ex) {
      logger.error("An error occured while saving employee info", ex);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      empResponseSingle.setStatus(Status.ERROR.toString());
    }
    return empResponseSingle;
  }

  public EmpResponse getAllEmployees(int page) {
    EmpResponse empResponse = new EmpResponse();
    try {
      PageRequest pr = PageRequest.of(page,5);
      Page<Employee> pageObj = employeeRepository.findAll(pr);
      Iterator<Employee> empItr = pageObj.iterator();
      empResponse = this.toEmpResponseData(empItr);
      empResponse.setTotalRecords(pageObj.getTotalElements());
      empResponse.setTotalPages(pageObj.getTotalPages());
    } catch(Exception ex) {
      logger.error("An error occured while fetching employee info for all employees", ex);
      empResponse.setStatus(Status.ERROR.toString());
    }
    return empResponse;
  }

  public EmpResponse searchEmployeeByName(String nameFilter, Integer page) {
    EmpResponse empResponse = null;
    try {
      PageRequest pr = PageRequest.of(page,5);
      Page<Employee> pageObj = employeeRepository.findByNameContaining(nameFilter.toLowerCase(Locale.ROOT), pr);
      Iterator<Employee> empItr = pageObj.iterator();
      empResponse = this.toEmpResponseData(empItr);
      empResponse.setTotalRecords(pageObj.getTotalElements());
      empResponse.setTotalPages(pageObj.getTotalPages());
    } catch(Exception ex) {
      logger.error("An error occured while searching employee info for all employees", ex);
      empResponse.setStatus(Status.ERROR.toString());
    }
    return empResponse;
  }

  private EmpResponse toEmpResponseData(Iterator<Employee> empItr) {
    EmpResponse empResponse = new EmpResponse();
    List<com.example.rqchallenge.model.Employee> employees = new ArrayList<>();
    while(empItr.hasNext()) {
      Employee employee = empItr.next();
      employees.add(Mapper.toEmployeeModel(employee));
    }
    empResponse.setData(employees);
    empResponse.setStatus(Status.SUCCESS.toString());

    return empResponse;
  }


  public EmpResponseSingle getEmployee(Integer id) {
    EmpResponseSingle empResponseSingle = new EmpResponseSingle();
    try {
      Optional<Employee> employee = employeeRepository.findById(id);
      if(employee.isPresent()) {
        empResponseSingle.setSingleEmp(Mapper.toEmployeeModel(employee.get()));
        empResponseSingle.setStatus(Status.SUCCESS.toString());
      } else {
        empResponseSingle.setStatus(Status.NO_DATA.toString());
      }

    } catch(Exception ex) {
      logger.error("An error occured while fetching employee info employee " + id.toString(), ex);
      empResponseSingle.setStatus(Status.ERROR.toString());
    }

    return empResponseSingle;
  }

  public EmpResponseStat deleteEmployee(Integer id) {
    EmpResponseStat empResponseStat = new EmpResponseStat();
    try {
      Optional<Employee> empEntity = employeeRepository.findById(id);
      if(!empEntity.isPresent()) {
        empResponseStat.setStatus(Status.NO_DATA.toString());
      } else {
        employeeRepository.deleteById(id);
        empResponseStat.setStatus(Status.SUCCESS.toString());
        String deleteMsg = empResponseStat.getDeleteMessage();
        empResponseStat.setDeleteMessage(String.format(deleteMsg, id));
      }
    } catch(Exception ex) {
      logger.error("An error occured while fetching employee info employee " + id.toString(), ex);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      empResponseStat.setStatus(Status.ERROR.toString());
    }
    return empResponseStat;
  }

  public EmpResponseStat getMaxSalary() {
    Integer maxSalary = null;
    EmpResponseStat responseMap = new EmpResponseStat();
    try {
      maxSalary = employeeRepository.findMaxSalary();
      if(Objects.isNull(maxSalary)) {
        responseMap.setStatus(Status.NO_DATA.toString());
      } else {
        responseMap.setStatus(Status.SUCCESS.toString());
        responseMap.setMaxSalary(maxSalary);
      }
    } catch(Exception ex) {
      logger.error("An error occured while fetching maximum salary", ex);
      responseMap.setStatus(Status.ERROR.toString());
    }

    return responseMap;
  }

  public EmpResponseStat getEmployeesWithTopTenSalary() {
    EmpResponseStat responseMap = new EmpResponseStat();
    List<String> topTen = new ArrayList<>();
    try{
      topTen = employeeRepository.findTopTenSalariedEmployees(PageRequest.of(0, 10));
      if(topTen.isEmpty()) {
        responseMap.setStatus(Status.NO_DATA.toString());
      } else {
        responseMap.setStatus(Status.SUCCESS.toString());
      }

    } catch(Exception ex) {
      logger.error("An error occured while fetching top 10 salaried employees", ex);
      responseMap.setStatus(Status.ERROR.toString());
    }

    responseMap.setTopTenSalaries(topTen);

    return responseMap;
  }


  public HttpStatus getHttpStatus(String status) {
    HttpStatus statusCode = HttpStatus.OK;
    if(status.equals(Status.ERROR.toString())) {
      statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    if(status.equals(Status.NO_DATA.toString())) {
      statusCode = HttpStatus.NO_CONTENT;
    }

    return statusCode;
  }
}
