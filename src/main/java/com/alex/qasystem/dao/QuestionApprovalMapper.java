package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionApproval;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Alex
 */
@Component
@Mapper
public interface QuestionApprovalMapper {

    @Insert("INSERT INTO question_approval(user_id, question_id, type, create_time, last_edit_time) VALUES" +
            "(#{userId}, #{questionId}, #{type}, #{createTime}, #{lastEditTime} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(QuestionApproval questionApproval);

    @Delete("DELETE FROM question_approval WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Update("<script>" +
            "UPDATE question_approval" +
            "<set>" +
            "<if test='userId != null'>        user_id        = #{userId},           </if>" +
            "<if test='questionId != null'>    question_id    = #{questionId},       </if>" +
            "<if test='type != null'>           type          = #{type},              </if>" +
            "<if test='createTime != null'>    create_time    = #{createTime},        </if>" +
            "<if test='lastEditTime != null'>  last_edit_time = #{lastEditTime},      </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(QuestionApproval questionApproval);

    @Select("SELECT * FROM question_approval WHERE id = #{id} ")
    @Results(id = "questionApprovalResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "questionId", column = "question_id", javaType = Integer.class),
            @Result(property = "type", column = "type", javaType = Integer.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class)
    })
    QuestionApproval selectById(@Param("id") Integer id);

    @Select("SELECT * FROM question_approval WHERE question_id = #{questionId} ")
    @ResultMap("questionApprovalResult")
    List<QuestionApproval> selectByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT COUNT(id) FROM question_approval WHERE question_id = #{questionId} and type = 0")
    @ResultType(Integer.class)
    Integer countApprovalsByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT COUNT(id) FROM question_approval WHERE question_id = #{questionId} and type = 1")
    @ResultType(Integer.class)
    Integer countDisapprovalsByQuestionId(@Param("questionId") Integer questionId);

}
