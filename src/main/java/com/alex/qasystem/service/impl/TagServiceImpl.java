package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.TagService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag getTagByTitle(String title) {
        return tagMapper.selectByTitle(title);
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Tag updateDescription(User user, Integer tagId, String title, String description) throws NotFoundException, AuthException {
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new NotFoundException("找不到该标签. tagId: " + tagId);
        }
        if (user.getGroupId() != 1) {
            throw new AuthException("没有更新标签描述的权限");
        }
        tag.setId(tagId);
        if (title != null) {
            tag.setTitle(title);
        }
        if (description != null) {
            tag.setDescription(description);
        }
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
    public Tag deleteTag(User user, Integer tagId) throws NotFoundException, AuthException {
        Tag tag = tagMapper.selectById(tagId);
        if (tag == null) {
            throw new NotFoundException("找不到该标签. tagId: " + tagId);
        }
        if (user.getGroupId() != 0) {
            throw new AuthException("没有删除标签的权限");
        }
        tagMapper.deleteById(tagId);
        return tag;
    }

}
