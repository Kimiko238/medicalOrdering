package com.ordering.service;

import com.ordering.model.User;
import com.ordering.repository.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

  private UserMapper userMapper;
  private PasswordEncoder passwordEncoder;

  public void save(User user) {
    String hashedPass = passwordEncoder.encode(user.getPass());
    user.setPass(hashedPass);
    userMapper.insert(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userMapper.findByName(username);
    if (user == null) {
      throw new UsernameNotFoundException("ユーザーが見つかりません。");
    }
    return new UserPrincipal(user);
  }
}
