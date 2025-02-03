package com.ordering.repository;

import com.ordering.model.Inspection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InspectionMapper {

  List<Inspection> selectAll();

  Inspection selectById(String id);
}


