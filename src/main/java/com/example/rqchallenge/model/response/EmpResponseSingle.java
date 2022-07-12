package com.example.rqchallenge.model.response;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpResponseSingle extends EmpResponse {
  public EmpResponseSingle() {
    super();
  }

  @JsonProperty("data")
  private Employee singleEmp;
}
