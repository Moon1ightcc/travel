package com.itfxp.travel.dao.impl;

import com.itfxp.travel.dao.UserDao;
import com.itfxp.travel.domain.User;
import com.itfxp.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/*
  用户的持久层（跟数据库进行交互）
 */
public class UserDaoImpl implements UserDao {
    // 创建jdbc工具类对象
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        try {
            // 定义sql语句 根据用户名查询用户表信息
            String sql = "select * from tab_user where username = ?";
            /*
            * 返回单个对象使用jdbcTemplate.queryForObject方法
            *  BeanPropertyRowMapper将数据库查询结果转换为Java类对象
            * 常应用于使用Spring的JdbcTemplate查询数据库，获取List结果列表，数据库表字段和实体类自动对应
            * */
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 用户信息添加
     * @param user
     */
    @Override
    public void addUser(User user) {
        // 编写sql语句
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getCode(),
                user.getStatus());
    }

    /**
     * 根据激活码查找用户信息
     * @param code
     * @return
     */
    @Override
    public User findCode(String code) {
        User user = new User();
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改用户指定激活状态
     * @param code
     */


    @Override
    public void updateStatus(String code) {
        String sql = "update tab_user set status = 'Y' where uid = ? ";
        jdbcTemplate.update(sql,code);

    }
}
