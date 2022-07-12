package com.example.rqchallenge.constants;

public enum Status {
  SUCCESS("success"), ERROR("error"), NO_DATA("no data");
  private String status;

  Status(String status) {
    this.status = status;
  }
}
