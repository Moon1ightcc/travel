package com.itfxp.travel.service.impl;

import com.itfxp.travel.dao.UserDao;
import com.itfxp.travel.dao.impl.UserDaoImpl;
import com.itfxp.travel.domain.User;
import com.itfxp.travel.service.UserService;
import com.itfxp.travel.util.MailUtils;
import com.itfxp.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    //  创建UserDao实现对象();
    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        // 1.根据用户的姓名，查有没有重名的用户名
        User isUser = userDao.findUserByUsername(user.getUsername());
        // 如果查询到用户的数据，封装到User对象，该对象如果不为null，则有数据查询到了用户信息了
        if (isUser != null) {
            return false; // 查询到了有重名的用户
        }

        // 2.保存用户信息
        // 2.1设置激活码,唯一字符串
        user.setCode(UuidUtil.getUuid());
        // 2.2设置激活状态
        user.setStatus("N");
        //System.out.println(user.getStatus());
        userDao.addUser(user);

        // 3.激活邮件发送，邮件正文
        // 3.1 设置内容
        String content = "<a href='http://localhost:8090/travel/activeUserServlet?code="+user.getCode()+"'>点击激活【途牛旅游网】</a>";
        //调用工具类中的sendMail方法发送邮件
        MailUtils.sendMail(user.getEmail(),content,"旅游网站账户激活邮箱");

        return true;
    }

    @Override
    public boolean active(String code) {
        // 根据code查询用户信息
        User user = userDao.findCode(code);
        //如果用户存在，则修改status为Y
        if(user != null){
            // 修改status为Y
            userDao.updateStatus(code);
            return true;
        }
        return false;
    }
}
