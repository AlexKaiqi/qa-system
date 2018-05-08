package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Bookmark;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BookmarkMapper {

    @Insert("INSERT INTO bookmark (user_id, question_id) VALUES" +
            "(#{userId}, #{questionId}) ")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(Bookmark bookmark);

    @Delete("DELETE FROM bookmark WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM bookmark WHERE user_id = #{userId}")
    Integer deleteByUserId(@Param("userId") Integer userId);

    @Delete("DELETE FROM bookmark WHERE question_id = #{questionId} ")
    Integer deleteByQuestionId(@Param("questionId") Integer questionId);

    @Select("SELECT * FROM bookmark WHERE id = #{id} ")
    @Results(id = "bookmarkResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "questionId", column = "question_id", javaType = Integer.class),
    })
    Bookmark selectById(@Param("id") Integer id);

    @Select("SELECT * FROM bookmark WHERE user_id = #{userId} ")
    @ResultMap("bookmarkResult")
    List<Bookmark> selectByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM bookmark WHERE question_id = #{questionId}")
    @ResultMap("bookmarkResult")
    List<Bookmark> selectByQuestionId(@Param("questionId") Integer questionId);

}
