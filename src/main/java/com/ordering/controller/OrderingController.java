package com.ordering.controller;

import com.ordering.model.Inspection;
import com.ordering.service.InspectionService;
import com.ordering.service.PatientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class OrderingController {

  public PatientService p_service;
  public InspectionService o_service;

  //検査依頼：一覧画面遷移時
  @GetMapping
  public String index(Model model) {
    model.addAttribute("name", "花子");
    List<Inspection> inspections = o_service.findAll();
    model.addAttribute("inspections", inspections);
    return "index";
  }

  //検査依頼：詳細画面遷移時
//  @PathVariable・・・Getマッピングによって取得してきた{id}をオブジェクトに入れる
  @GetMapping("/inspectionDetails/{id}")
  public String inspectionDetails(@PathVariable("id") String userId, Model model) {
    Inspection inspection = o_service.findById(userId);
    inspection.setNumber(0);
    model.addAttribute("inspection", inspection);
    return "inspectionDetails";
  }

  //  検査依頼：編集画面への遷移時
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") String inspectionId, Model model) {
    Inspection inspection = o_service.findById(inspectionId);
    inspection.setNumber(1);
    model.addAttribute("inspection", inspection);
    return "orderForm";
  }


  //  検査依頼：新規登録画面遷移時
  @GetMapping("/inspection")
  public String newOrder(Model model, Inspection inspection) {
    inspection.setNumber(0);
    return "orderForm";
  }

  //  検査依頼：新規登録時
  @PostMapping("/inspectionSubmit")
  public String index(Model model, @Validated Inspection inspection,
      BindingResult bindingResult) {
    //入力された内容のチェック（条件分岐）
    if (bindingResult.hasErrors()) {
      // エラーがある場合、フォームに戻る
      model.addAttribute("message", "登録できていません");
      return "orderForm";
    }
    if (inspection.getNumber() == 0) {
      //新規登録時の分岐
      o_service.save(inspection);
      model.addAttribute("message", "登録しました");
      Inspection inspections = o_service.findById(inspection.getId());
      model.addAttribute("inspections", inspections);
      return "index";
    } else {
      //更新処理時の分岐
      o_service.edit(inspection);
      model.addAttribute("message", "更新しました");
      Inspection inspections = o_service.findById(inspection.getId());
      model.addAttribute("inspections", inspections);
      return "index";
    }
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") String inspectionId,
      Model model) {
    Inspection inspection = o_service.findById(inspectionId);
    o_service.delete(inspection);
    model.addAttribute("message", "削除しました");
    return "index";
  }

}
