package com.alex.qasystem.service;

import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;

public interface TagService {

    Tag getTagByTitle(String title);

    Tag getTagById(Integer id);

    Tag updateDescription(User user, Integer id, String description) throws NotFoundException, AuthException;

    Tag addTag(User user, String title, String description);

    Tag deleteTag(User user, Integer tagId) throws NotFoundException, AuthException;
}
