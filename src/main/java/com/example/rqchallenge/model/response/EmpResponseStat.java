package com.example.rqchallenge.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpResponseStat extends EmpResponse {

  public EmpResponseStat() {
    super();
  }
  private Integer maxSalary;

  private List<String> topTenSalaries;

  private String deleteMessage = "Successfully deleted record# %d";

}
