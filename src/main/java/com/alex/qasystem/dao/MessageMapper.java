package com.alex.qasystem.dao;

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

/*    @Delete("DELETE FROM message WHERE status = 1")
    Integer deleteAllRead();*/

    @Update("<script>" +
            "UPDATE message" +
            "<set>" +
            "<if test='type != null'>         type         = #{type},            </if>" +
            "<if test='receiverId != null'>          receiver_id           = #{receiverId},             </if>" +
            "<if test='content != null'>    content     = #{content},       </if>" +
            "<if test='sendTime != null'>     send_time     = #{sendTime},         </if>" +
            "<if test='receiveTime != null'>   receive_time  = #{receiveTime},       </if>" +
            "<if test='status != null'>         status          = #{status},             </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    Integer updateById(Message Message);

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
    Message selectById(@Param("id") Integer id);

    @Select("SELECT * FROM message WHERE receiver_id = #{receiverId} ")
    @ResultMap("messageResult")
    List<Message> selectByReceiverId(@Param("receiverId") Integer receiverId);

    @Select("SELECT * FROM message WHERE receiver_id = #{recieverId} and status = 0")
    @ResultMap("messageResult")
    List<Message> selectUnreadByReceiverId(@Param("receiverId") Integer receiverId);

}
