package com.alex.qasystem.dao;

import com.alex.qasystem.entity.AnswerComment;
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
public interface AnswerCommentMapper {
    @Insert("INSERT INTO answer_comment(user_id, answer_id, content, create_time, last_edit_time) VALUES" +
            "(#{userId}, #{answerId}, #{content}, #{createTime}, #{lastEditTime} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(AnswerComment answerComment);

    @Delete("DELETE FROM answer_comment WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM answer_comment WHERE answer_id = #{answerId}")
    Integer deleteByAnswerId(@Param("answerId") Integer answerId);

    @Update("<script>" +
            "UPDATE answer_comment" +
            "<set>" +
            "<if test='userId != null'>        user_id        = #{userId},           </if>" +
            "<if test='answerId != null'>    answer_id    = #{answerId},       </if>" +
            "<if test='content != null'>       content        = #{content},           </if>" +
            "<if test='createTime != null'>    create_time    = #{createTime},        </if>" +
            "<if test='lastEditTime != null'>  last_edit_time = #{lastEditTime},      </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(AnswerComment answerComment);

    @Select("SELECT * FROM answer_comment WHERE id = #{id} ")
    @Results(id = "answerCommentResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "answerId", column = "answer_id", javaType = Integer.class),
            @Result(property = "content", column = "content", javaType = String.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.alex.qasystem.dao.UserMapper.selectSimpleById"))

    })
    AnswerComment selectById(@Param("id") Integer id);

    @Select("SELECT * FROM answer_comment WHERE answer_id = #{answerId} ")
    @ResultMap("answerCommentResult")
    List<AnswerComment> selectByAnswerId(@Param("answerId") Integer answerId);

    @Select("SELECT COUNT(id) FROM answer_comment WHERE user_id = #{userId}")
    @ResultType(Integer.class)
    Integer countCommentsByUserId(@Param("userId") Integer userId);


}
