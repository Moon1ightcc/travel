package com.itfxp.travel.service;

import com.itfxp.travel.domain.User;

public interface UserService {
    /**
     *注册用户
     * @Param user
     * @return
     */

    boolean register(User user);

    boolean active(String code);
}
