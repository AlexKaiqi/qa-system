package com.alex.qasystem.dao;

import com.alex.qasystem.entity.UserSubscription;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserSubscriptionMapper {

    @Insert("INSERT INTO user_subscription (user_id, watched_user_id) VALUES" +
            "(#{userId}, #{watchedUserId}) ")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(UserSubscription userSubscription);

    @Delete("DELETE FROM user_subscription WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM user_subscription WHERE user_id = #{userId}")
    Integer deleteByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM user_subscription WHERE user_id = #{userId} and watched_user_id = #{watchedUserId}")
    Integer deleteByUserIdAndWatchedUserId(@Param("userId") Integer userId, @Param("watchedUserId") Integer watchedUserId);

    @Select("SELECT * FROM user_subscription WHERE id = #{id} ")
    @Results(id = "userSubscriptionResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "watchedUserId", column = "watched_user_id", javaType = Integer.class),
    })
    UserSubscription selectById(@Param("id") Integer id);

    @Select("SELECT * FROM user_subscription WHERE user_id = #{userId} ")
    @ResultMap("userSubscriptionResult")
    List<UserSubscription> selectByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM user_subscription WHERE watched_user_id = #{watchedUserId}")
    @ResultMap("userSubscriptionResult")
    List<UserSubscription> selectByWatchedUserId(@Param("watchedUserId") Integer watchedUserId);

    @Select("SELECT * FROM user_subscription WHERE user_id = #{userId} and watched_user_id = #{watchedUserId} limit 1")
    @ResultMap("userSubscriptionResult")
    UserSubscription selectByUserIdAndWatchedUserId(@Param("userId") Integer userId, @Param("watchedUserId") Integer watchedUserId);

}
