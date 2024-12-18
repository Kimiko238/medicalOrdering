package com.ordering.controller;

import com.ordering.form.OrderForm;
import com.ordering.model.Patient;
import com.ordering.service.PatientService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class OrderingController {

  public PatientService service;

  @GetMapping
  public String index(Model model) {
    model.addAttribute("name", "花子");
    model.addAttribute("OrderForm", new OrderForm());
    Patient p = new Patient("1", "花子", LocalDate.now(), "女", "太郎", LocalDateTime.now(), null,
        null, null, null);
    service.save(p);
    Patient patient = service.findById("1");
    return "index";
  }

  @PostMapping("/inspectionSubmit")
  public String index(Integer id, Model model, @Validated OrderForm form,
      BindingResult bindingResult) {
    model.addAttribute("message", "登録しました");
    model.addAttribute("OrderForm", form);
    if (bindingResult.hasErrors()) {
      // エラーがある場合、フォームに戻る
      return "index";
    }
    return "index";
  }


}
