package com.ordering.controller;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.exception.PatientAlreadyExistsException;
import com.ordering.model.Patient;
import com.ordering.service.PatientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class PatientController {

  private PatientService patientService;


  //  患者新規作成画面の遷移
  @GetMapping("/newPatient")
  public String newPatient(Model model, Authentication authentication) {
    model.addAttribute("patient", new Patient());
    boolean newPatient = true;
    model.addAttribute("newPatient", newPatient);
    return "patientForm";
  }

  //  患者編集画面の遷移
  @GetMapping("/editPatient/{id}")
  public String editPatient(Model model,
      @PathVariable String id) {
    Patient patient = patientService.findById(id);
    model.addAttribute("patient", patient);
    boolean newPatient = false;
    model.addAttribute("newPatient", newPatient);
    return "patientForm";
  }

  //  患者検索の遷移(患者詳細画面へ）
  @GetMapping("/searchPatient")
  public String searchPatient(@RequestParam("showId") Integer showId,
      Model model,
      RedirectAttributes redirectAttributes) {
    try {
      Patient patient = patientService.findByShowId(showId);
      model.addAttribute("patient", patient);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("searchedMessage", "このIDで患者は登録されていません");
      return "redirect:/";
    }

    List<FormInspectionOrderDto> formInspectionOrderDtos = patientService.viewInspectionList(
        showId);
//    リストが空だった場合の処理
    boolean zeroList = false;
    if (formInspectionOrderDtos.isEmpty()) {
      zeroList = true;
    }

    //modelへ登録する
    model.addAttribute("formInspectionOrderDtos", formInspectionOrderDtos);
    model.addAttribute("zeroList", zeroList);
    return "patientDetails";
  }


  //  患者新規作成
  @PostMapping("/createPatient")
  public String createPatient(RedirectAttributes redirectAttributes,
      @Validated Patient patient,
      BindingResult bindingResult,
      Authentication authentication,
      Model model) {
    if (bindingResult.hasErrors()) {
      Boolean newPatient = true;
      model.addAttribute("newPatient", newPatient);
      return "patientForm";
    }

    try {
      Patient createdPatient = patientService.save(patient, authentication);
      redirectAttributes.addFlashAttribute("message", "患者は登録できました");
      redirectAttributes.addFlashAttribute("showId", createdPatient.getShowId());
    } catch (PatientAlreadyExistsException e) {
      redirectAttributes.addFlashAttribute("message", "この患者は登録済みです");
    }

    return "redirect:newPatient";
  }

  //  患者更新
  @PostMapping("/updatePatient")
  public String createCompPatient(RedirectAttributes redirectAttributes,
      @Validated Patient patient,
      BindingResult bindingResult,
      @AuthenticationPrincipal UserDetails userDetails) {
    if (bindingResult.hasErrors()) {
      return "patientForm";
    }
    patientService.update(patient, userDetails.getUsername());
    redirectAttributes.addFlashAttribute("message", "患者情報を更新しました");
    redirectAttributes.addFlashAttribute("patient", patient);
    redirectAttributes.addAttribute("showId", patient.getShowId());

    return "redirect:searchPatient";
  }
}
