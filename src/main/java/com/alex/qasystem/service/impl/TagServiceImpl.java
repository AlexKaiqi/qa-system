package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.management.RuntimeMBeanException;

@Service
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag getTagByTitle(String title) {
        //TODO
        return null;
    }

    @Override
    public Tag getTagById(Integer id) {

        //TODO
        return null;
    }

    @Override
    public Tag updateDescription(User user, Integer tagId, String description) {
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new RuntimeException("找不到该标签. tagId: " + tagId);
        }
        if (user.getGroupId() != 1) {
            throw new RuntimeException("没有更新标签描述的权限");
        }
        tag.setDescription(description);
        tagMapper.updateById(tag);
        return tag;
    }

    @Override
    public Tag addTag(User user, String title, String description) {
        Tag tag = new Tag();
        tag.setTitle(title);
        tag.setDescription(description);
        tagMapper.insert(tag);
        return tag;
    }

    @Override
    public Tag deleteTag(User user, Integer tagId) {
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new RuntimeException("找不到该标签. tagId: " + tagId);
        }
        if (user.getGroupId() != 0) {
            throw new RuntimeException("没有删除标签的权限");
        }
        tagMapper.deleteById(tagId);
        return tag;
    }

}
