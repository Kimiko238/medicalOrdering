package com.ordering.service;

import com.ordering.model.Inspection;
import com.ordering.repository.InspectionMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class InspectionService {

  private InspectionMapper inspectionMapper;

  //  新規保存時
  public void save(Inspection inspection) {
    inspectionMapper.insert(inspection);
  }


  //  全件取得
  public List<Inspection> findAll() {
    return inspectionMapper.selectAll();
  }

  //  検査の詳細を取得
  public Inspection findById(String id) {

    return inspectionMapper.selectById(id);
  }

  //  検査の詳細を編集
  public void edit(Inspection inspection) {
    inspectionMapper.update(inspection);
  }

  public void delete(Inspection inspection) {
    inspectionMapper.delete(inspection);
  }
}


