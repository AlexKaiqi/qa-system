package com.alex.qasystem.dao;

import com.alex.qasystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Alex
 */
@Component
@Mapper
public interface UserMapper {

    /**
     * @param username 用户名
     * @return 符合条件的用户
     */
    @Select("select * from user where username = #{username} and password = #{password")
    @Results({
        @Result(property = "id", column = "id", javaType = Long.class),
        @Result(property = "username", column = "username", javaType = String.class),
        @Result(property = "password", column = "password", javaType = String.class),
        @Result(property = "profileName", column = "profile_name", javaType = String.class),
        @Result(property = "profileImgSrc", column = "profile_img_src", javaType = String.class),
        @Result(property = "email", column = "email", javaType = String.class),
        @Result(property = "groupId", column = "group_id", javaType = Integer.class),
        @Result(property = "reputation", column = "reputation", javaType = Integer.class),
        @Result(property = "registerTime", column = "register_time", javaType = Date.class),
        @Result(property = "lastLoginTime", column = "last_login_time", javaType = Date.class),
        @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
        @Result(property = "status", column = "status", javaType = Integer.class),
    })
    User selectUserByUsernameAndPassword(String username, String password);

    /**
     * 新增用户
     * @param user 用户实体
     */
    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password}, #{profileName)")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insertUser(User user);

    @Update("UPDATE user SET password = #{password} where id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE id= #{id}")
    void deleteUser(int id);


}
