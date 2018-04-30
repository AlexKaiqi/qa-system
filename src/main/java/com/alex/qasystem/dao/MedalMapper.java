package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Medal;
import com.alex.qasystem.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Alex
 */
@Component
@Mapper
public interface MedalMapper {

    @Insert("INSERT INTO medal(title, description, icon_src) VALUES" +
            "(#{title}, #{description}, #{iconSrc})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(Medal medal);

    @Delete("DELETE FROM medal WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Update("UPDATE medal SET title = #{title}, description = #{description}, icon_src = #{iconSrc} WHERE id = #{id}")
    Integer updateById(Medal medal);

    @Select("SELECT * FROM medal WHERE id = #{id} ")
    @Results(id = "medalResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "title", column = "title", javaType = String.class),
            @Result(property = "description", column = "description", javaType = String.class),
            @Result(property = "iconSrc", column = "icon_src", javaType = String.class)
    })
    Medal selectById(Integer id);
}
