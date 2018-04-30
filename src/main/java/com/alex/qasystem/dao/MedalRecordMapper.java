package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Medal;
import com.alex.qasystem.entity.MedalRecord;
import com.alex.qasystem.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Alex
 */
@Component
@Mapper
public interface MedalRecordMapper {
    @Insert("INSERT INTO medal_record(medal_id, user_id, award_time) VALUES" +
            "(#{medalId}, #{userId}, #{awardTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(MedalRecord medalRecord);

    @Delete("DELETE FROM medal_record WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Select("SELECT * FROM medal_record WHERE id = #{id} ")
    @Results(id = "medalRecordResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "medalId", column = "medal_id", javaType = Integer.class),
            @Result(property = "userId", column = "user_id", javaType = Integer.class),
            @Result(property = "awardTime", column = "award_time", javaType = Integer.class)
    })
    MedalRecord selectById(@Param("id") Integer id);

    @Select("SELECT * FROM medal_record WHERE user_id = #{userId} ")
    @ResultMap("medalRecordResult")
    List<MedalRecord> selectByUserId(@Param("userId") Integer userId);
}
