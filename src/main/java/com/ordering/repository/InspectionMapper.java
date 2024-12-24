package com.ordering.repository;

import com.ordering.model.Inspection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InspectionMapper {

  void insert(Inspection inspection);

  List<Inspection> selectAll();

  Inspection selectById(String id);

  void update(Inspection inspection);

  void delete(Inspection inspection);
}
