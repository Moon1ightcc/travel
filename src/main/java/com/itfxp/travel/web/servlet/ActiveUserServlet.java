package com.itfxp.travel.web.servlet;

import com.itfxp.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取激活码
        String code = req.getParameter("code");
        if (code != null){
            // 2.调用service完成激活
            UserServiceImpl userService = new UserServiceImpl();
            boolean flag =userService.active(code);

            // 3.判断标记
            String msg = null;

            if (flag) {
                // 激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else{
                msg = "激活失败！";
            }
            //响应数据
            resp.setContentType("text/html;charset=utf-8");
            //不是写到文件中的,而是写到页面上,展示信息。
            resp.getWriter().write(msg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
