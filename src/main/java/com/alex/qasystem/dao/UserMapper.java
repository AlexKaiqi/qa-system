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
     * 根据邮箱和密码查询用户
     *
     * @param email    邮箱
     * @return 符合条件的 User
     */
    @Select("select * from user where email = #{email}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "email", column = "email", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class),
            @Result(property = "profileName", column = "profile_name", javaType = String.class),
            @Result(property = "profileImgSrc", column = "profile_img_src", javaType = String.class),
            @Result(property = "groupId", column = "group_id", javaType = Integer.class),
            @Result(property = "reputation", column = "reputation", javaType = Integer.class),
            @Result(property = "registerTime", column = "register_time", javaType = Date.class),
            @Result(property = "lastLoginTime", column = "last_login_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
    })
    User selectByEmail(@Param("email") String email);

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "email", column = "email", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class),
            @Result(property = "profileName", column = "profile_name", javaType = String.class),
            @Result(property = "profileImgSrc", column = "profile_img_src", javaType = String.class),
            @Result(property = "groupId", column = "group_id", javaType = Integer.class),
            @Result(property = "reputation", column = "reputation", javaType = Integer.class),
            @Result(property = "registerTime", column = "register_time", javaType = Date.class),
            @Result(property = "lastLoginTime", column = "last_login_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
    })
    User selectById(@Param("id") Integer id);

    /**
     * 插入新的用户
     *
     * @param user User 实例
     * @return 插入数据条数, 正确返回应该为 1
     */
    @Insert("INSERT INTO user(email, password, profile_name, profile_img_src, group_id, reputation, register_time, last_login_time, last_edit_time, status) " +
            "VALUES(#{email}, #{password}, #{profileName}, #{profileImgSrc}, #{groupId}, #{reputation}, #{registerTime}, #{lastLoginTime}, #{lastEditTime}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insert(User user);

    /**
     * 根据 ID 删除用户
     *
     * @param id 用户 ID
     * @return  删除的用户数量, 正确返回结果应该是 1.
     */
    @Delete("DELETE FROM user WHERE id= #{id}")
    Integer deleteById(Integer id);

}
