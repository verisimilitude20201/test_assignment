package com.example.rqchallenge.repository;

import com.example.rqchallenge.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {

  @Query(value = "SELECT MAX(salary) FROM Employee")
  public Integer findMaxSalary();

  @Query(value = "SELECT name FROM Employee ORDER BY salary DESC")
  public List<String> findTopTenSalariedEmployees(Pageable pageable);

  @Query(nativeQuery = true, value ="SELECT * from Employee as e where LOWER(e.name) like %:name%")
  Page<Employee> findByNameContaining(@Param("name") String nameFilter, Pageable pageable);

}