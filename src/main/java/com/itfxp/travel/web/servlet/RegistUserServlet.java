package com.itfxp.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itfxp.travel.domain.ResultInfo;
import com.itfxp.travel.domain.User;
import com.itfxp.travel.service.UserService;
import com.itfxp.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registerUserServlet")
public class RegistUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // 判断验证码
        String check = req.getParameter("check");
        //从seesion中获取验证
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        // 比较
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper= new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }

        //request.getParameterMap可以将request中的参数和值变成一个Map
        Map<String,String[]> map = req.getParameterMap();

        //封装数据
        User user = new User();
        try {
             //BeanUtils.populate( Object bean, Map properties):
             //遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean属性
            BeanUtils.populate(user,map);

            System.out.println("user"+user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service中的方法处理逻辑
        UserService userService = new UserServiceImpl();
        boolean flag = userService.register(user);

        //根据service中的方法返回结果，进行封装数据
        // true代表注册成功 false代表注册失败
        ResultInfo resultInfo = new ResultInfo();
        if(flag){
            //封装数据
            resultInfo.setFlag(true);
        }else {
            // 封装数据，设置提示信息。
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败！");
        }

        // writeValueAsString(obj)将对象装化成json数据，进行响应,同时要设置响应头
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);

        // 设置响应头,
        resp.setContentType("application/json;charset=utf-8");

        // 写数据
        // resp.getWriter().write() 一般用于响应ajax请求放回数据
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
