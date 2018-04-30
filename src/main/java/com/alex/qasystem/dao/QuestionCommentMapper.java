package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionComment;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Alex
 */
@Component
@Mapper
public interface QuestionCommentMapper {

    @Insert("INSERT INTO question_comment(user_id, question_id, content, create_time, last_edit_time) VALUES" +
            "(#{userId}, #{questionId}, #{content}, #{createTime}, #{lastEditTime} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(QuestionComment questionComment);

    @Delete("DELETE FROM question_comment WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM question_comment WHERE question_id = #{questionId}")
    Integer deleteByQuestionId(@Param("questionId") Integer questionId);

    @Update("<script>" +
            "UPDATE question_comment" +
            "<set>" +
            "<if test='userId != null'>        user_id        = #{userId},           </if>" +
            "<if test='questionId != null'>    question_id    = #{questionId},       </if>" +
            "<if test='content != null'>       content        = #{content},           </if>" +
            "<if test='createTime != null'>    create_time    = #{createTime},        </if>" +
            "<if test='lastEditTime != null'>  last_edit_time = #{lastEditTime},      </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(QuestionComment questionComment);

    @Select("SELECT * FROM question_comment WHERE id = #{id} ")
    @Results(id = "questionCommentResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "questionId", column = "question_id", javaType = Integer.class),
            @Result(property = "content", column = "content", javaType = String.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.alex.qasystem.dao.UserMapper.selectSimpleById"))

    })
    QuestionComment selectById(@Param("id") Integer id);

    @Select("SELECT * FROM question_comment WHERE question_id = #{questionId} ")
    @ResultMap("questionCommentResult")
    List<QuestionComment> selectByQuestionId(@Param("questionId") Integer questionId);


    @Select("SELECT COUNT(id) FROM question_comment WHERE user_id = #{userId}")
    @ResultType(Integer.class)
    Integer countCommentsByUserId(@Param("userId") Integer userId);
}
