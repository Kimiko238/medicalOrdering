package com.ordering.controller;

import com.ordering.model.Inspection;
import com.ordering.service.InspectionService;
import com.ordering.service.PatientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class InspectionController {

  private PatientService patientService;
  private InspectionService inspectionService;

  //検査依頼：一覧画面遷移時
  @GetMapping
  public String index(Model model, Authentication authentication) {
    model.addAttribute("name", "花子");
    List<Inspection> inspections = inspectionService.findAll();
    model.addAttribute("inspections", inspections);
    model.addAttribute("authentication", authentication);
    return "index";
  }

  //検査依頼：詳細画面遷移時
//  @PathVariable・・・Getマッピングによって取得してきた{id}をオブジェクトに入れる
  @GetMapping("/inspectionDetails/{id}")
  public String inspectionDetails(@PathVariable("id") String userId, Model model) {
    Inspection inspection = inspectionService.findById(userId);
    inspection.setNumber(0);
    model.addAttribute("inspection", inspection);
    return "inspectionDetails";
  }

  //  検査依頼：編集画面への遷移時
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") String inspectionId, Model model) {
    Inspection inspection = inspectionService.findById(inspectionId);
    inspection.setNumber(1);
    model.addAttribute("inspection", inspection);
    return "orderForm";
  }


  //  検査依頼：新規登録画面遷移時
  @GetMapping("/newInspectionView")
  public String newOrder(Model model, Inspection inspection) {
    inspection.setNumber(0);
    return "orderForm";
  }

  //  検査依頼：新規登録時
  @PostMapping("/inspectionSubmit")
  public String index(Model model, RedirectAttributes redirectAttributes,
      @Validated Inspection inspection,
      BindingResult bindingResult, Authentication authentication) {
    //入力された内容のチェック（条件分岐）
    if (bindingResult.hasErrors()) {
      // エラーがある場合、フォームに戻る
      return "orderForm";
    }
    if (inspection.getNumber() == 0) {
      //新規登録時の分岐
      inspectionService.save(inspection, authentication);
      redirectAttributes.addFlashAttribute("message", "登録しました");
      Inspection inspections = inspectionService.findById(inspection.getId());
      redirectAttributes.addFlashAttribute("inspections", inspections);
      return "redirect:/";
    } else {
      //更新処理時の分岐
      inspectionService.edit(inspection, authentication);
      redirectAttributes.addFlashAttribute("message", "更新しました");
      Inspection inspections = inspectionService.findById(inspection.getId());
      redirectAttributes.addFlashAttribute("inspections", inspections);
      return "redirect:/";
    }
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") String inspectionId,
      Model model, Authentication authentication) {
    Inspection inspection = inspectionService.findById(inspectionId);
    inspectionService.delete(inspection);
    model.addAttribute("authentication", authentication);
    model.addAttribute("message", "削除しました");
    return "index";
  }

}
