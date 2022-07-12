package com.example.rqchallenge.employee;

import com.example.rqchallenge.model.response.EmpResponse;
import com.example.rqchallenge.model.response.EmpResponseSingle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/employees")
public interface IEmployeeController {

    @GetMapping()
    ResponseEntity<EmpResponse> getAllEmployees(@RequestParam Integer page);

    @GetMapping("/search/{searchString}")
    ResponseEntity<EmpResponse> getEmployeesByNameSearch(@PathVariable String searchString, @RequestParam("page") Integer page);

    @GetMapping("/{id}")
    ResponseEntity<EmpResponseSingle> getEmployeeById(@PathVariable Integer id);

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<EmpResponseSingle> createEmployee(@RequestBody Map<String, String> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id);

}
