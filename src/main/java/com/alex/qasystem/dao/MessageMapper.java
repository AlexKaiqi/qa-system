package com.alex.qasystem.dao;

import com.alex.qasystem.entity.MedalRecord;
import com.alex.qasystem.entity.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface MessageMapper {
    @Insert("INSERT INTO message (type, receiver_id, content, send_time, receive_time, status) VALUES" +
            "(#{type}, #{receiverId}, #{content}, #{sendTime}, #{receiveTime}, #{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(Message message);

    @Delete("DELETE FROM message WHERE id = #{id} ")
    Integer deleteById(@Param("id") Integer id);

    @Delete("DELETE FROM message WHERE status = 1")
    Integer deleteAllRead();

    @Select("SELECT * FROM message WHERE id = #{id} ")
    @Results(id = "messageResult", value = {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "type", column = "type", javaType = Integer.class),
            @Result(property = "receiverId", column = "receiver_id", javaType = Integer.class),
            @Result(property = "content", column = "content", javaType = String.class),
            @Result(property = "sendTime", column = "send_time", javaType = Date.class),
            @Result(property = "receiveTime", column = "receive_time", javaType = Date.class),
            @Result(property = "status", column = "status", javaType = Integer.class)
    })
    MedalRecord selectById(@Param("id") Integer id);

    @Select("SELECT * FROM message WHERE receiver_id = #{receiverId} ")
    @ResultMap("messageResult")
    List<Message> selectByReceiverId(@Param("receiverId") Integer receiverId);

    @Select("SELECT * FROM message WHERE receiver_id = #{recieverId} and status = 0")
    @ResultMap("messageResult")
    List<Message> selectUnreadByReceiverId(@Param("receiverId") Integer receiverId);

}
