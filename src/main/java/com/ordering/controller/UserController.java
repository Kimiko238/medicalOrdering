package com.ordering.controller;

import com.ordering.exception.UserAlreadyExistsException;
import com.ordering.model.User;
import com.ordering.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {

  private UserService userService;


  //ユーザーのログイン画面遷移
  @GetMapping("/login")
  public String login(Authentication authentication, Model model) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/";
    }
    return "login";
  }

  //ユーザー登録画面遷移
  @GetMapping("/userRegister")
  public String userRegister(User user) {

    return "userRegister";
  }

  //  新規登録処理
  @PostMapping("/createUser")
  public String createUser(Model model, @Validated User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "userRegister";
    }
    try {
      userService.save(user);
      model.addAttribute("message", "登録できました");
    } catch (UserAlreadyExistsException e) {
      model.addAttribute("message", "このユーザーはすでに登録済みです。");
    }
    return "login";
  }
}
