package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionSubscription;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionSubscriptionMapper {

    @Insert("INSERT INTO question_subscription (user_id, question_id) VALUES" +
            "(#{userId}, #{questionId})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(QuestionSubscription questionSubscription);

    @Delete("DELETE FROM question_subscription WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM question_subscription WHERE user_id = #{userId}")
    Integer deleteByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM question_subscription WHERE question_id = #{questionId} ")
    Integer deleteByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT * FROM question_subscription WHERE id = #{id} ")
    @Results(id = "questionSubscriptionResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "questionId", column = "question_id", javaType = Integer.class),
    })
    QuestionSubscription selectById(@Param("id") Integer id);

    @Select("SELECT * FROM question_subscription WHERE user_id = #{userId} ")
    @ResultMap("questionSubscriptionResult")
    List<QuestionSubscription> selectByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM question_subscription WHERE question_id = #{questionId}")
    @ResultMap("questionSubscriptionResult")
    List<QuestionSubscription> selectByQuestionId(@Param("questionId") Integer questionId);

}
