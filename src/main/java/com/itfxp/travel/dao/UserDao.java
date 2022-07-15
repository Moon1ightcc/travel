package com.itfxp.travel.dao;

import com.itfxp.travel.domain.User;

public interface UserDao {

    // 根据用户名查询用户
     User findUserByUsername(String username);

    // 添加用户
     void addUser(User user);

     // 根据状态码查询用户
      User findCode(String code);

      void updateStatus(String code);
}
