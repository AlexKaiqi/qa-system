package com.alex.qasystem.dao;

import com.alex.qasystem.entity.AnswerApproval;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Alex
 */
@Component
@Mapper
public interface AnswerApprovalMapper {

    @Insert("INSERT INTO answer_approval(user_id, answer_id, type, create_time, last_edit_time) VALUES" +
            "(#{userId}, #{answerId}, #{type}, #{createTime}, #{lastEditTime} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(AnswerApproval answerApproval);

    @Delete("DELETE FROM answer_approval WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM answer_approval WHERE answer_id = #{answerId}")
    Integer deleteByAnswerId(@Param("answerId") Integer answerId);

    @Update("<script>" +
            "UPDATE answer_approval" +
            "<set>" +
            "<if test='userId != null'>        user_id        = #{userId},           </if>" +
            "<if test='answerId != null'>    answer_id    = #{answerId},       </if>" +
            "<if test='type != null'>           type          = #{type},              </if>" +
            "<if test='createTime != null'>    create_time    = #{createTime},        </if>" +
            "<if test='lastEditTime != null'>  last_edit_time = #{lastEditTime},      </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(AnswerApproval answerApproval);

    @Select("SELECT * FROM answer_approval WHERE id = #{id} ")
    @Results(id = "answerApprovalResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "answerId", column = "answer_id", javaType = Integer.class),
            @Result(property = "type", column = "type", javaType = Integer.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class)
    })
    AnswerApproval selectById(@Param("id") Integer id);

    @Select("SELECT * FROM answer_approval WHERE answer_id = #{answerId} ")
    @ResultMap("answerApprovalResult")
    List<AnswerApproval> selectByAnswerId(@Param("answerId") Integer answerId);

    @Select("SELECT COUNT(id) FROM answer_approval WHERE answer_id = #{answerId} and type = 0")
    @ResultType(Integer.class)
    Integer countApprovalsByAnswerId(@Param("answerId") Integer answerId);

    @Select("SELECT COUNT(id) FROM answer_approval WHERE answer_id = #{answerId} and type = 1")
    @ResultType(Integer.class)
    Integer countDisapprovalsByAnswerId(@Param("answerId") Integer answerId);
}