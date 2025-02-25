package com.ordering.controller;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.exception.OrderStatusException;
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

  //検査依頼：検査一覧画面遷移時
  @GetMapping
  public String index(Model model) {
    List<FormInspectionOrderDto> formInspectionOrderDtos = orderService.findAll();
    boolean isZeroList = false;
    if (formInspectionOrderDtos.isEmpty()) {
      isZeroList = true;
    }
    model.addAttribute("formInspectionOrdersDto", formInspectionOrderDtos);
    model.addAttribute("zeroList", isZeroList);
    return "index";
  }

  //検査依頼：詳細画面遷移時
  //  @PathVariable・・・Getマッピングによって取得してきた{id}をオブジェクトに入れる
  @GetMapping("/orderDetails/{id}")
  public String inspectionDetails(@PathVariable("id") String orderId,
      @RequestParam(value = "from", required = false) String from,
      Model model,
      Authentication authentication) {
    FormInspectionOrderDto formInspectionOrderDto = orderService.findById(orderId);
    model.addAttribute("formInspectionOrderDto", formInspectionOrderDto);
    model.addAttribute("authentication", authentication);
    model.addAttribute("from", from);
    return "orderDetails";
  }

  //  検査依頼：編集画面への遷移時
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") String inspectionId, Model model) {
    FormInspectionOrderDto formInspectionOrderDto = orderService.findById(inspectionId);
    model.addAttribute("formInspectionOrderDto", formInspectionOrderDto);
    return "orderForm";
  }


  //  検査依頼：新規登録画面遷移時
  @GetMapping("/newOrder")
  public String newOrder(Model model,
      @RequestParam int showId,
      Authentication authentication) {
    FormInspectionOrderDto formInspectionOrderDto = orderService.settingOrder(showId);
    model.addAttribute("formInspectionOrderDto", formInspectionOrderDto);
    model.addAttribute("authentication", authentication);
    return "orderForm";
  }

  //  検査依頼：新規登録時
  @PostMapping("/newOrderSubmit")
  public String newSubmit(Model model,
      RedirectAttributes redirectAttributes,
      @Validated FormInspectionOrderDto formInspectionOrderDto,
      BindingResult bindingResult,
      Authentication authentication) {
    //入力された内容のチェック（条件分岐）
    if (bindingResult.hasErrors()) {
      // エラーがある場合、フォームに戻る
      return "orderForm";
    }
    FormInspectionOrderDto showFormOrder = orderService.save(formInspectionOrderDto,
        authentication);
    redirectAttributes.addFlashAttribute("message", "登録しました");
    redirectAttributes.addFlashAttribute("formInspectionOrderDto", showFormOrder);
    return "redirect:/";
  }

  //検査依頼：編集登録時
  @PostMapping("/editOrderSubmit")
  public String editSubmit(Model model,
      RedirectAttributes redirectAttributes,
      @Validated FormInspectionOrderDto editOrderDto,
      BindingResult bindingResult,
      Authentication authentication) {
    if (bindingResult.hasErrors()) {
      return "orderForm";
    }
    //更新処理時の分岐
    FormInspectionOrderDto updatedOrderDto = orderService.edit(editOrderDto, authentication);
    redirectAttributes.addFlashAttribute("message", "更新しました");
    redirectAttributes.addFlashAttribute("formInspectionOrderDto", updatedOrderDto);
    return "redirect:/";
  }

  //依頼の削除
  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") String inspectionId,
      Model model, Authentication authentication) {
    FormInspectionOrderDto formInspectionOrderDto = orderService.findById(inspectionId);
    orderService.delete(formInspectionOrderDto);
    boolean isZeroList = true;
    model.addAttribute("message", "削除しました");
    model.addAttribute("zeroList", isZeroList);
    return "index";
  }

  //  オーダーのステータスを変更する
  @PostMapping("/statusEditOrder/{id}")
  public String statusEdit(
      @RequestParam(value = "from", required = false) String from,
      @RequestParam(value = "clickStatus", required = false) String clickStatus,
      FormInspectionOrderDto formInspectionOrderDto,
      RedirectAttributes redirectAttributes
  ) {
    try {
      FormInspectionOrderDto editedDto = orderService.setStatus(formInspectionOrderDto,
          clickStatus);
      redirectAttributes.addFlashAttribute("from", from);
      redirectAttributes.addFlashAttribute("clickStatus", clickStatus);
    } catch (OrderStatusException e) {
      redirectAttributes.addFlashAttribute("message",
          "この検査ステータスはすでに「受付済」か「実施済」、または「受付」せずに「実施済」をしようとしています。");
    }
    return "redirect:/";
  }
}
