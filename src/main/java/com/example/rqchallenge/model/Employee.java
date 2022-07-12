package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Employee {
  private Integer id;

  private String name;

  private Integer salary;

  private Integer age;

  @JsonProperty("profile_image")
  private String profileImage;
}
