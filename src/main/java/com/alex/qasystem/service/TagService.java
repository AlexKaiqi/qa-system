package com.alex.qasystem.service;

import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;

public interface TagService {

    Tag getTagByTitle(String title);
    Tag getTagById(Integer id);
    Tag updateDescription(User user, Integer id, String description);
    Tag addTag(User user, String title, String description);
    Tag deleteTag(User user, Integer tagId);
}
