package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(user_id, title, description, status, create_time, last_edit_time) VALUES" +
            "(#{userId}, #{title}, #{description}, #{status}, #{createTime}, #{lastEditTime} )")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(Question question);

    @Insert({
            "<script>",
            "INSERT INTO question_tag (question_id, tag_id)",
            "values ",
            "<foreach  collection='tagIds' item='tagId' separator=','>",
            "( #{questionId}, #{tagId})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insertTagReferences(@Param("questionId") Integer questionId, @Param("tagIds") List<Integer> tagIds);

    @Delete("DELETE FROM question WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM question_tag WHERE question_id = #{questionId} ")
    Integer deleteTagReferencesByQuestionId(@Param("questionId") Integer questionId);

    @Update("<script>" +
            "UPDATE question" +
            "<set>" +
            "<if test='userId != null'>         user_id         = #{userId},            </if>" +
            "<if test='title != null'>          title           = #{title},             </if>" +
            "<if test='description != null'>    description     = #{description},       </if>" +
            "<if test='status != null'>         status          = #{status},             </if>" +
            "<if test='createTime != null'>     create_time     = #{createTime},         </if>" +
            "<if test='lastEditTime != null'>   last_edit_time  = #{lastEditTime},       </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(Question question);

    @Select("SELECT * FROM question WHERE id = #{id}")
    @Results(id = "questionResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "status", column = "status", javaType = Integer.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class),
            @Result(property = "lastEditTime", column = "last_edit_time", javaType = Date.class),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "com.alex.qasystem.dao.UserMapper.selectSimpleById")),
            @Result(property = "tags", column = "id", javaType = List.class, many = @Many(select = "com.alex.qasystem.dao.TagMapper.selectByQuestionId")),
            @Result(property = "questionComments", column = "id", javaType = List.class, many = @Many(select = "com.alex.qasystem.dao.QuestionCommentMapper.selectByQuestionId")),
            @Result(property = "answers", column = "id", javaType = List.class, many = @Many(select = "com.alex.qasystem.dao.AnswerMapper.selectByQuestionId")),
            @Result(property = "approvals", column = "id", javaType = Integer.class, one = @One(select = "com.alex.qasystem.dao.QuestionApprovalMapper.countApprovalsByQuestionId")),
            @Result(property = "disapprovals", column = "id", javaType = Integer.class, one = @One(select = "com.alex.qasystem.dao.QuestionApprovalMapper.countDisapprovalsByQuestionId"))

    })
    Question selectById(@Param("id") Integer id);

    @Select("SELECT * FROM question WHERE title REGEXP #{titleRegexp} ")
    @ResultMap("questionResult")
    List<Question> selectByTitleRegexp(@Param("titleRegexp") String titleRegexp);

    @Select(
            "SELECT * FROM question WHERE id IN" +
                    "   (SELECT question_id FROM question_tag WHERE question_tag.tag_id IN " +
                    "       (SELECT tag.id FROM tag where tag.title REGEXP #{tagRegexp})" +
                    "   )"
    )
    @ResultMap("questionResult")
    List<Question> selectByTagRegexp(@Param("tagRegexp") String tagRegexp);

    @Select("SELECT * FROM question WHERE user_id = #{userId}")
    @ResultMap("questionResult")
    List<Question> selectByUserId(@Param("userId") Integer userId);
}
