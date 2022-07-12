package com.example.rqchallenge.model.response;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpResponse {
  private List<Employee> data = new ArrayList<>();

  @JsonProperty("total_records")
  private Long totalRecords;

  @JsonProperty("total_pages")
  private Integer totalPages;

  protected String status;

  protected String message;


}
