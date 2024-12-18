package com.ordering.repository;

import com.ordering.model.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PatientMapper {

  /**
   * 指定されたIDに対応する「すること」を取得します。
   */
  Patient selectById(@Param("id") String id);

  void insert(Patient patient);
}

