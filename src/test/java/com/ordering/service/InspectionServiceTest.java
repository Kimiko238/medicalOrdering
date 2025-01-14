package com.ordering.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ordering.model.Inspection;
import com.ordering.repository.InspectionMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class InspectionServiceTest {

  @InjectMocks
  InspectionService inspectionService;

  @Mock
  InspectionMapper inspectionMapper;

  @Mock
  Authentication authentication;

  Inspection inspectionSample;

  @BeforeEach
  void setUp() {
    inspectionSample = new Inspection(
        0,
        "id",
        "検査",
        "2024-12-22 11:05:15",
        "検査の詳細",
        "アシュ",
        null,// createdAt
        null,          // updatedBy
        null,          // updatedAt
        null,          // deletedBy
        null
    );
  }

  //  新規保存のテスト
  @Test
  void saveTest() {
    doNothing().when(inspectionMapper).insert(inspectionSample);
    inspectionService.save(inspectionSample, authentication);
    verify(inspectionMapper, times(1)).insert(inspectionSample);
  }

  //  全件取得のテスト
  @Test
  void findAllTest() {
    doReturn(List.of(inspectionSample)).when(inspectionMapper).selectAll();
    List<Inspection> inspectionDtoList = inspectionService.findAll();
    Inspection inspectionDto = inspectionDtoList.stream().findFirst().orElseThrow();
    assertEquals(inspectionDto.getId(), inspectionSample.getId());
    assertEquals(inspectionDto.getName(), inspectionSample.getName());
    assertEquals(inspectionDto.getDate(), inspectionSample.getDate());
    assertEquals(inspectionDto.getDetails(), inspectionSample.getDetails());
  }

  //  検査の詳細取得のテスト
  @Test
  void findByIdTest() {
    String id = "id";
    doReturn(inspectionSample).when(inspectionMapper).selectById(id);
    Inspection inspectionDto = inspectionMapper.selectById(id);
    assertEquals(inspectionDto.getId(), inspectionSample.getId());
    assertEquals(inspectionDto.getName(), inspectionSample.getName());
    assertEquals(inspectionDto.getDate(), inspectionSample.getDate());
    assertEquals(inspectionDto.getDetails(), inspectionSample.getDetails());
  }

  //検査の詳細を編集する、のテスト
  @Test
  void editTest() {
    doNothing().when(inspectionMapper).update(inspectionSample);
    inspectionService.edit(inspectionSample, authentication);
    verify(inspectionMapper, times(1)).update(inspectionSample);
  }

  //検査を削除する、のテスト
  @Test
  void deleteTest() {
    doNothing().when(inspectionMapper).delete(inspectionSample);
    inspectionService.delete(inspectionSample);
    verify(inspectionMapper, times(1)).delete(inspectionSample);
  }
}
