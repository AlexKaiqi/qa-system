package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Answer;
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
public interface AnswerMapper {
    @Insert("INSERT INTO answer(user_id, question_id, content, status, create_time, last_edit_time) VALUES" +
            "(#{userId}, #{questionId}, #{content}, #{status}, #{createTime}, #{lastEditTime} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(Answer answer);

    @Delete("DELETE FROM answer WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM answer WHERE question_id = #{questionId} ")
    Integer deleteByQuestionId(@Param("questionId") Integer questionId);

    @Update("<script>" +
            "UPDATE answer" +
            "<set>" +
            "<if test='userId != null'>         user_id         = #{userId},            </if>" +
            "<if test='questionId != null'>     question_id     = #{questionId},        </if>" +
            "<if test='content != null'>        content         = #{content},           </if>" +
            "<if test='status != null'>         status          = #{status},             </if>" +
            "<if test='createTime != null'>     create_time     = #{createTime},         </if>" +
            "<if test='lastEditTime != null'>   last_edit_time  = #{lastEditTime},       </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(Answer answer);

    @Select("SELECT * FROM answer WHERE id = #{id}")
    @Results(id = "answerResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "questionId", column = "question_id", javaType = Integer.class),
            @Result(property = "content", column = "content", javaType = String.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.alex.qasystem.dao.UserMapper.selectSimpleById")),
            @Result(property = "answerComments", column = "id", javaType = List.class, many = @Many(select = "com.alex.qasystem.dao.AnswerCommentMapper.selectByAnswerId")),
            @Result(property = "approvals", column = "id", javaType = Integer.class, one = @One(select = "com.alex.qasystem.dao.AnswerApprovalMapper.countApprovalsByAnswerId")),
            @Result(property = "disapprovals", column = "id", javaType = Integer.class, one = @One(select = "com.alex.qasystem.dao.AnswerApprovalMapper.countDisapprovalsByAnswerId"))
    })
    Answer selectById(@Param("id") Integer id);

    @Select("SELECT * FROM answer WHERE question_id = #{questionId}")
    @ResultMap("answerResult")
    List<Answer> selectByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT * FROM answer WHERE content REGEXP #{contentRegexp} ")
    @ResultMap("answerResult")
    List<Answer> selectByContentRegexp(@Param("contentRegexp") String contentRegexp);

    @Select("SELECT * FROM answer WHERE user_id = #{userId}")
    @ResultMap("answerResult")
    List<Answer> selectByUserId(@Param("userId") Integer userId);
}
