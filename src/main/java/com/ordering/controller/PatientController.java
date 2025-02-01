package com.ordering.controller;

import com.ordering.entity.Patient;
import com.ordering.exception.PatientAlreadyExistsException;
import com.ordering.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class PatientController {

  private PatientService patientService;

  //  患者新規作成画面の遷移
  @GetMapping("/newPatient")
  public String newPatient(Model model) {
    model.addAttribute("patient", new Patient());
    return "patientForm";
  }

  //  患者編集画面の遷移
  @GetMapping("/editPatientView")
  public String editPatient(Model model, Authentication authentication) {
    model.addAttribute("authentication", authentication);
    return null;
  }

  //  患者検索の遷移
  @GetMapping("/searchPatient")
  public String searchPatient(
      @RequestParam("showId") Integer showId,
      Model model
  ) {
    Patient patient = patientService.findById(showId);
    model.addAttribute("patient", patient);
    System.out.println(patient.getShowId());
    return "patientDetails";
  }


  //  患者新規作成
  @PostMapping("/createPatient")
  public String createPatient(
      RedirectAttributes redirectAttributes,
      @Validated Patient patient,
      BindingResult bindingResult,
      Authentication authentication
  ) {
    if (bindingResult.hasErrors()) {
      return "redirect:newPatient";
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
  public String createCompPatient(@Validated Patient patient) {
    patientService.findById(patient.getShowId());
    return "redirect:newPatient";
  }
}
