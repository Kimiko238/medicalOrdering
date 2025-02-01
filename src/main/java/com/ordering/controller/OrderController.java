package com.ordering.controller;

import com.ordering.dto.EntityInspectionOrderDto;
import com.ordering.dto.FormInspectionOrderDto;
import com.ordering.entity.InspectionOrder;
import com.ordering.model.InspectionOrderSummary;
import com.ordering.service.OrderService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class OrderController {

  private OrderService orderService;

  //検査依頼：一覧画面遷移時
  @GetMapping
  public String index(Model model) {
    List<InspectionOrderSummary> inspectionOrderSummaryList = orderService.findInspectionOrderSummaryList();
    model.addAttribute(inspectionOrderSummaryList);
    return "index";
  }

  //検査依頼：詳細画面遷移時
  //  @PathVariable・・・Getマッピングによって取得してきた{id}をオブジェクトに入れる
  @GetMapping("/inspectionDetails/{id}")
  public String inspectionDetails(
      @PathVariable("id") String orderId,
      Model model
  ) {
    InspectionOrderSummary inspectionOrderSummary = orderService.findInspectionOrderSummary(orderId)
        .orElse(null);
    model.addAttribute("inspectionOrderSummary", inspectionOrderSummary);
    return "inspectionDetails";
  }

  //  検査依頼：編集画面への遷移時
  @GetMapping("/edit/{id}")
  public String edit(
      @PathVariable("id") String inspectionId,
      Model model
  ) {
    InspectionOrder inspectionOrder = orderService.findById(inspectionId);
    model.addAttribute("order", inspectionOrder);
    return "orderForm";
  }


  //  検査依頼：新規登録画面遷移時
  @GetMapping("/newOrder")
  public String newOrder(
      Model model,
      @RequestParam String patientId
  ) {
    FormInspectionOrderDto formInspectionOrderDto = orderService.settingOrder(patientId);
    model.addAttribute("formInspectionOrderDto", formInspectionOrderDto);
    return "orderForm";
  }

  //  検査依頼：新規登録時
  @PostMapping("/newOrderSubmit")
  public String newSubmit(
      RedirectAttributes redirectAttributes,
      @Validated EntityInspectionOrderDto entityInspectionOrderDto,
      BindingResult bindingResult,
      Authentication authentication) {
    //入力された内容のチェック（条件分岐）
    if (bindingResult.hasErrors()) {
      // エラーがある場合、フォームに戻る
      return "orderForm";
    }
    orderService.save(entityInspectionOrderDto, authentication);
    redirectAttributes.addFlashAttribute("message", "登録しました");
    return "redirect:/";
  }

  //検査依頼：編集登録時
  @PostMapping("/editInspectionSubmit")
  public String editSubmit(
      RedirectAttributes redirectAttributes,
      @Validated InspectionOrder inspectionOrder,
      BindingResult bindingResult,
      Authentication authentication) {
    if (bindingResult.hasErrors()) {
      return "orderForm";
    }
    //更新処理時の分岐
    orderService.edit(inspectionOrder, authentication);
    redirectAttributes.addFlashAttribute("message", "更新しました");
    InspectionOrder inspectionOrderCorrection = orderService.findById(inspectionOrder.getId());
    redirectAttributes.addFlashAttribute("orderCorrection", inspectionOrderCorrection);
    return "redirect:/";
  }

  //依頼の削除
  @GetMapping("/delete/{id}")
  public String delete(
      @PathVariable("id") String inspectionId,
      Model model
  ) {
    InspectionOrder inspectionOrder = orderService.findById(inspectionId);
    orderService.delete(inspectionOrder);
    model.addAttribute("message", "削除しました");
    return "index";
  }

}
