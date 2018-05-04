package com.alex.qasystem.dao;

import com.alex.qasystem.entity.AuthToken;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Alex
 */
@Component
@Mapper
public interface AuthTokenMapper {

    /**
     * ���� AuthToken
     *
     * @param authToken AuthToken ʵ��
     * @return ������ AuthToken ID
     */
    @Insert("INSERT INTO auth_token(user_id, user_group_id, token, create_time, expire_time) " +
            "VALUES (#{userId}, #{userGroupId}, #{token}, #{createTime}, #{expireTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(AuthToken authToken);

    /**
     * ɾ�����ڵ� token
     *
     * @return ɾ���Ĺ��ڵ� token ������
     */
    @Delete("DELETE FROM auth_token WHERE expire_time < sysdate()")
    Integer deleteExpiredToken();

    @Update("UPDATE auth_token SET expire_time = #{expireTime} WHERE token = #{token}")
    Integer updateExpireTimeByToken(@Param("token") String token, @Param("expireTime") Date expireTime);

    /**
     * ����token��ѯ AuthToken
     *
     * @param token token
     * @return ���������� AuthToken
     */
    @Select("SELECT * FROM auth_token WHERE token = #{token}")
    @Results(id = "authTokenResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "userGroupId", column = "user_group_id", javaType = Integer.class),
            @Result(property = "token", column = "token", javaType = String.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "expireTime", column = "expire_time", javaType = Date.class),
    })
    AuthToken selectByToken(String token);

    @Select("SELECT * FROM auth_token WHERE user_id = #{userId} limit 1")
    @ResultMap("authTokenResult")
    AuthToken selectByUserId(Integer userId);
}
