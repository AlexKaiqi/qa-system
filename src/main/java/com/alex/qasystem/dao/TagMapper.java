package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TagMapper {

    @Insert("INSERT INTO tag(title, description, icon_src) VALUES" +
            "(#{title}, #{description}, #{iconSrc})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(Tag tag);

    @Delete("DELETE FROM tag WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Update("<script>" +
            "UPDATE tag" +
            "<set>" +
            "<if test='title != null'>          title           = #{title},             </if>" +
            "<if test='description != null'>    description     = #{description},       </if>" +
            "<if test='iconSrc != null'>        icon_src        = #{iconSrc}            </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(Tag tag);

    @Select("SELECT * FROM tag WHERE id = #{id} ")
    @Results(id = "tagResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "iconSrc", column = "icon_src", javaType = String.class)
    })
    Tag selectById(Integer id);

    @Select("SELECT * FROM tag WHERE title = #{title} ")
    @ResultMap("tagResult")
    Tag selectByTitle(@Param("title") String title);

    @Select("SELECT * FROM tag WHERE id IN " +
            "   (SELECT tag_id FROM question_tag WHERE question_id = #{questionId}) ORDER BY id")
    @ResultMap("tagResult")
    List<Tag> selectByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT * FROM tag")
    @ResultMap("tagResult")
    List<Tag> selectAll();
}
