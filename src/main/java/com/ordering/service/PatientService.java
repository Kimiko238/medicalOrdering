package com.ordering.service;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.exception.PatientNullException;
import com.ordering.helper.OrderConvert;
import com.ordering.model.Order;
import com.ordering.model.Patient;
import com.ordering.repository.OrderMapper;
import com.ordering.repository.PatientMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PatientService {


  private PatientMapper patientMapper;
  private OrderMapper orderMapper;
  private OrderConvert orderConvert;

//  @Autowired
//  public PatientService(PatientMapper patientMapper,
//      OrderMapper orderMapper,
//      OrderConvert orderConvert) {
//    this.patientMapper = patientMapper;
//    this.orderMapper = orderMapper;
//    this.orderConvert = orderConvert;
//  }


  //  連番IDから患者情報を取得
  public Patient findByShowId(Integer showId) {
    Patient searchedPatient = patientMapper.selectByShowId(showId);
    if (Objects.isNull(searchedPatient)) {
      throw new PatientNullException();
    }
    return searchedPatient;
  }

  //  患者ID（String型）から情報を取得
  public Patient findById(String id) {
    return patientMapper.selectById(id);
  }


  //  重複登録がないかチェックする処理
  public Patient checkNameAndBirthday(Patient patient, Authentication authentication) {
    return patientMapper.selectByNameAndBirthday(patient.getName(), patient.getBirthday());
  }


  public boolean existPatient(String name, String birthday) {
    return patientMapper.selectByNameAndBirthday(name, birthday) != null;

  }


  //患者の新規作成
  public Patient save(Patient patient, Authentication authentication) {
    boolean existPatient = existPatient(patient.getName(), patient.getBirthday());
    if (existPatient) {
      throw new RuntimeException();
    } else {
      patient.setCreatedBy(authentication.getName());
      patientMapper.insert(patient);
    }

    return findByNameAndBirthday(patient.getName(), patient.getBirthday());
  }

  public Patient findByNameAndBirthday(String name, String birthDay) {
    return patientMapper.selectByNameAndBirthday(name, birthDay);
  }

  // 患者idに絞った検査一覧を取得
  public List<FormInspectionOrderDto> viewInspectionList(int showId) {
    List<Order> patientOrders = orderMapper.selectAllByPatientId(showId);
    List<FormInspectionOrderDto> patientFormDtos = new ArrayList<>();
    for (Order patientOrder : patientOrders) {
      FormInspectionOrderDto patientFormDto = orderConvert.convertForm(patientOrder);
      patientFormDtos.add(patientFormDto);
    }
    return patientFormDtos;
  }


  public void update(Patient editPatient, String userName) {
    editPatient.setUpdatedBy(userName);
    patientMapper.update(editPatient);
  }
}
