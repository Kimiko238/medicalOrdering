package com.ordering.controller;

import com.ordering.entity.RequestInspectionOrderDto;
import com.ordering.entity.ResponseInspectionOrderDto;
import com.ordering.model.Order;
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
  public String index(Model model, Authentication authentication) {
    List<Order> orders = orderService.findAll();
    model.addAttribute("orders", orders);
    model.addAttribute("authentication", authentication);
    return "index";
  }

  //検査依頼：詳細画面遷移時
  //  @PathVariable・・・Getマッピングによって取得してきた{id}をオブジェクトに入れる
  @GetMapping("/inspectionDetails/{id}")
  public String inspectionDetails(@PathVariable("id") String userId, Model model) {
    Order order = orderService.findById(userId);
    model.addAttribute("order", order);
    return "inspectionDetails";
  }

  //  検査依頼：編集画面への遷移時
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") String inspectionId, Model model) {
    Order order = orderService.findById(inspectionId);
    model.addAttribute("order", order);
    return "orderForm";
  }


  //  検査依頼：新規登録画面遷移時
  @GetMapping("/newOrder")
  public String newOrder(Model model,
      @RequestParam int showId) {
    RequestInspectionOrderDto requestInspectionOrderDto = orderService.settingOrder(showId);
    model.addAttribute("requestInspectionOrderDto", requestInspectionOrderDto);
    return "orderForm";
  }

  //  検査依頼：新規登録時
  @PostMapping("/newOrderSubmit")
  public String newSubmit(Model model,
      RedirectAttributes redirectAttributes,
      @Validated ResponseInspectionOrderDto responseInspectionOrderDto,
      BindingResult bindingResult,
      Authentication authentication) {
    //入力された内容のチェック（条件分岐）
    if (bindingResult.hasErrors()) {
      // エラーがある場合、フォームに戻る
      return "orderForm";
    }
    orderService.save(responseInspectionOrderDto, authentication);
    redirectAttributes.addFlashAttribute("message", "登録しました");
    Order orderDto = orderService.findById(responseInspectionOrderDto.getOrderId());
    redirectAttributes.addFlashAttribute("orderDto", orderDto);
    return "redirect:/";
  }

  //検査依頼：編集登録時
  @PostMapping("/editInspectionSubmit")
  public String editSubmit(Model model,
      RedirectAttributes redirectAttributes,
      @Validated Order order,
      BindingResult bindingResult,
      Authentication authentication) {
    if (bindingResult.hasErrors()) {
      return "orderForm";
    }
    //更新処理時の分岐
    orderService.edit(order, authentication);
    redirectAttributes.addFlashAttribute("message", "更新しました");
    Order orderCorrection = orderService.findById(order.getId());
    redirectAttributes.addFlashAttribute("orderCorrection", orderCorrection);
    return "redirect:/";
  }

  //依頼の削除
  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") String inspectionId,
      Model model, Authentication authentication) {
    Order order = orderService.findById(inspectionId);
    orderService.delete(order);
    model.addAttribute("authentication", authentication);
    model.addAttribute("message", "削除しました");
    return "index";
  }

}
