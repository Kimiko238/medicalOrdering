package com.ordering.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public String handleException(Exception ex, Model model) {
    model.addAttribute("errorMessage", ex.getMessage());
    return "error";
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public String handleNoResourceFoundException(Exception ex, Model model) {
    model.addAttribute(
        "errorMessage"  ,
        "そんなページは存在しません（NoResourceFoundExceptionが発生）。具体的なエラーメッセージ: "
            + ex.getMessage());
    return "error";
  }
}