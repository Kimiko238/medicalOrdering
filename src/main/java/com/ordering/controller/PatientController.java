package com.ordering.controller;

import com.ordering.model.Patient;
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
  public String newPatient(Model model, Authentication authentication, Patient patient) {
    model.addAttribute("authentication", authentication);
    model.addAttribute("patient", new Patient());

    return "patientForm";
  }

  //  患者編集画面の遷移
  @GetMapping("/editPatientView")
  public String editPatient(Model model, Authentication authentication, Patient patient) {
    model.addAttribute("authentication", authentication);
    return null;
  }

  //  患者検索の遷移
  @GetMapping("/searchPatient")
  public String searchPatient(@RequestParam("showId") Integer showId,
      Model model) {
    Patient patient = patientService.findById(showId);
    model.addAttribute("patient", patient);
    model.addAttribute("readOnly", true);
    return "patientDetails";
  }


  //  患者新規作成
  @PostMapping("/createPatient")
  public String createPatient(RedirectAttributes redirectAttributes, @Validated Patient patient,
      BindingResult bindingResult,
      Authentication authentication) {
    if (bindingResult.hasErrors()) {
      return "redirect:newPatient";
    }
    String message = patientService.checkNameAndBirthday(patient, authentication);
    redirectAttributes.addFlashAttribute("message", message);
    redirectAttributes.addFlashAttribute("authentication", authentication);
    redirectAttributes.addFlashAttribute("patient", patient);
    return "redirect:newPatient";
  }

  //  患者更新
  @PostMapping("/updatePatient")
  public String createCompPatient(RedirectAttributes redirectAttributes, @Validated Patient patient,
      BindingResult bindingResult, Authentication authentication) {
    patientService.findById(patient.getShowId());
    return "redirect:newPatient";
  }
}
