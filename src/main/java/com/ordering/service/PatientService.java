package com.ordering.service;

import com.ordering.model.Patient;
import com.ordering.repository.PatientMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PatientService {

  private PatientMapper patientMapper;

  public Patient findById(Integer showId) {
    return patientMapper.selectById(showId);
  }

  public String checkNameAndBirthday(Patient patient, Authentication authentication) {
    Patient nameAndBirthday = patientMapper.selectByNameAndBirthday(patient.getName(),
        patient.getBirthday());
    if (nameAndBirthday != null) {
      String messageAlready = "この患者は登録済みです";
      return messageAlready;
    } else {
      this.save(patient, authentication);
      String messagePass = "登録が完了しました！";
      return messagePass;
    }
  }

  //患者の新規作成
  public void save(Patient patient, Authentication authentication) {
    patient.setCreatedBy(authentication.getName());
    patientMapper.insert(patient);
  }
}
