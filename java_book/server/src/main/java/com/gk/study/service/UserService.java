package com.gk.study.service;


import com.gk.study.common.APIResponse;
import com.gk.study.entity.User;
import com.gk.study.pojo.DTO.LoginFormDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    List<User> getUserList(String keyword);
    User getAdminUser(User user);
    User getNormalUser(User user);
    void createUser(User user);
    void deleteUser(String id);

    void updateUser(User user);

    User getUserByToken(String token);
    User getUserByUserName(String username);

    User getUserDetail(Long userId);

    APIResponse sendCode(String phone);

    APIResponse login(LoginFormDTO loginFormDTO);

    void logout(HttpServletRequest request);
}
