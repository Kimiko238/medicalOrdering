package com.ordering.rest.controller;

import com.ordering.model.Inspection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestOrderController {

  @GetMapping("/api/order")
  public ResponseEntity<Inspection> getOrder(Inspection inspection) {
    Inspection inspectionForResponse = new Inspection(
        inspection.getId(),
        inspection.getName(),
        inspection.getCreatedBy(),
        inspection.getCreatedAt(),
        inspection.getUpdatedBy(),
        inspection.getUpdatedAt(),
        inspection.getDeletedBy(),
        inspection.getDeletedAt()
    );
    return new ResponseEntity<>(inspectionForResponse, HttpStatus.OK);
  }



  

}
