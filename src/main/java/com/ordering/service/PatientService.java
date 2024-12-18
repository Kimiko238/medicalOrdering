package com.ordering.service;

import com.ordering.model.Patient;
import com.ordering.repository.PatientMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PatientService {

  private PatientMapper patientMapper;

  public Patient findById(String id) {
    return patientMapper.selectById(id);
  }

  public void save(Patient patient) {
    patientMapper.insert(patient);
  }
}
