package com.example.rqchallenge.controller;

import com.example.rqchallenge.constants.Status;
import com.example.rqchallenge.employee.IEmployeeController;
import com.example.rqchallenge.model.response.EmpResponse;
import com.example.rqchallenge.model.response.EmpResponseSingle;
import com.example.rqchallenge.model.response.EmpResponseStat;
import com.example.rqchallenge.service.EmpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/employee")
@Api(value = "EmployeeController")
public class EmployeeController implements IEmployeeController {
  @Autowired
  private EmpService empService;

  @Override
  @GetMapping("/")
  @ApiOperation(value = "Get list of Employees in the System, paginated. Default page size is 5. ", response = EmpResponse.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 204, message = "No employee data exists"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<EmpResponse> getAllEmployees(@RequestParam(name = "page", defaultValue = "0") Integer page)  {
    EmpResponse empResponse = empService.getAllEmployees(page);
    return new ResponseEntity(empResponse, empService.getHttpStatus(empResponse.getStatus()));
  }

  @Override
  @GetMapping("/search/{searchString}")
  @ApiOperation(value = "Search Employees by name. Search string must contain 3 characters minimum. Default page size is 5.", response = EmpResponse.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 400, message = "Search string contains less than 3 characters"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<EmpResponse> getEmployeesByNameSearch(String searchString, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    if(searchString.isEmpty() || searchString.length() < 3) {
      EmpResponse empResponse = new EmpResponse();
      empResponse.setStatus(Status.ERROR.toString());
      empResponse.setMessage("Search string needs to be minimum 3 characters");

      return new ResponseEntity(empResponse, HttpStatus.BAD_REQUEST);
    }
    EmpResponse empResponse = empService.searchEmployeeByName(searchString, page);

    return new ResponseEntity(empResponse, empService.getHttpStatus(empResponse.getStatus()));
  }

  @Override
  @GetMapping("/{id}")
  @ApiOperation(value = "Return details of a single employee", response = EmpResponseSingle.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 400, message = "Invalid integer passed"),
          @ApiResponse(code = 404, message = "Employee does'nt exist for the passed id"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<EmpResponseSingle> getEmployeeById(Integer id) {
    EmpResponseSingle empResponseSingle = empService.getEmployee(id);
    if(empResponseSingle.getStatus().equals(Status.NO_DATA.toString())) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity(empResponseSingle, empService.getHttpStatus(empResponseSingle.getStatus()));
  }

  @Override
  @GetMapping("/highestSalary")
  @ApiOperation(value = "Returns the current maximum salary of all employees", response = Integer.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 204, message = "No data found"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
    EmpResponseStat empResponseStat  = empService.getMaxSalary();
    if(empResponseStat.getStatus().equals(Status.NO_DATA)) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity(empResponseStat.getMaxSalary(),
        empService.getHttpStatus(empResponseStat.getStatus()));
  }

  @Override
  @GetMapping("/topTenHighestEarningEmployeeNames")
  @ApiOperation(value = "Returns the names of employees with top 10 highest salaries", response = ArrayList.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 204, message = "No data found"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
    EmpResponseStat empResponseStat  = empService.getEmployeesWithTopTenSalary();
    if(empResponseStat.getStatus().equals(Status.NO_DATA)) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity(
            empResponseStat.getTopTenSalaries(),
            empService.getHttpStatus(empResponseStat.getStatus()));
  }

  @Override
  @PostMapping("/create")
  @ApiOperation(value = "Creates a new employee", response = EmpResponseSingle.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<EmpResponseSingle> createEmployee(Map<String, String> employeeInput) {
    EmpResponseSingle empResponseSingle = empService.saveAndReturnNewEmp(employeeInput);

    return new ResponseEntity(empResponseSingle, empService.getHttpStatus(empResponseSingle.getStatus()));
  }

  @DeleteMapping("/delete/{id}")
  @ApiOperation(value = "Deletes the employee with the provided ID", response = String.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Success"),
          @ApiResponse(code = 404, message = "Given employee does not exist"),
          @ApiResponse(code = 500, message = "Internal Server Error")})
  public ResponseEntity<String> deleteEmployeeById(Integer id) {
    EmpResponseStat empResponseStat  = empService.deleteEmployee(id);
    if(empResponseStat.getStatus().equals(Status.NO_DATA.toString())) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity(empResponseStat.getDeleteMessage(), empService.getHttpStatus(empResponseStat.getStatus()));
  }


}
