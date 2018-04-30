package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagMapperTest {
    @Autowired
    private TagMapper tagMapper;

    @Test
    @Transactional
    public void insert() {
        Tag tag = new Tag();
        tag.setTitle("test tag");
        tag.setDescription("test content");
        assertThat(tagMapper.insert(tag), is(1));
        System.out.println(tagMapper.selectById(tag.getId()));
    }

    @Test(expected = DuplicateKeyException.class)
    @Transactional
    public void insertDuplicateTitle() throws DuplicateKeyException {
        Tag tag = new Tag();
        tag.setTitle("java");
        tagMapper.insert(tag);
    }

    @Test
    @Transactional
    public void deleteById() {
        Tag tag = new Tag();
        tag.setTitle("test tag");
        tag.setDescription("test content");
        assertThat(tagMapper.insert(tag), is(1));
        assertThat(tagMapper.deleteById(tag.getId()), is(1));
        assertNull(tagMapper.selectById(tag.getId()));
    }

    @Test
    @Transactional
    public void updateById() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTitle("test");
        tag.setDescription("test info");
        tag.setIconSrc("/images/tag/test.jpg");
        assertThat(tagMapper.updateById(tag), is(1));
        tag = tagMapper.selectById(1);
        assertThat(tag.getTitle(), is("test"));
        assertThat(tag.getDescription(), is("test info"));
        assertThat(tag.getIconSrc(), is("/images/tag/test.jpg"));
        System.out.println(tag);
    }

    @Test
    @Transactional
    public void selectById() {
        Tag tag = tagMapper.selectById(1);
        assertThat(tag.getTitle(), is("java"));
        System.out.println(tag);
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        List<Tag> tags = tagMapper.selectByQuestionId(1);
        assertThat(tags.size(), is(2));
        System.out.println(tags);
    }
}