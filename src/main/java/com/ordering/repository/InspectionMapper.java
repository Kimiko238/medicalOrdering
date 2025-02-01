package com.ordering.repository;

import com.ordering.entity.InspectionType;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InspectionMapper {

  List<InspectionType> selectAll();

  InspectionType selectById(String id);
}


