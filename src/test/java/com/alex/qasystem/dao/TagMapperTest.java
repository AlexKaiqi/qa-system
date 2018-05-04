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
        assertThat(tagMapper.selectById(tag.getId()).toString(), is(tag.toString()));
        System.out.println(tagMapper.selectById(tag.getId()));
    }

    @Test(expected = DuplicateKeyException.class)
    @Transactional
    public void insertDuplicateTitle() throws DuplicateKeyException {
        Tag tag = new Tag();
        tag.setTitle("java");
        System.out.println(tagMapper.selectByTitle("java"));
        assertThat(tagMapper.insert(tag), is(0));

    }

    @Test
    @Transactional
    public void deleteById() {
        Tag tag = new Tag();
        tag.setTitle("test tag");
        tag.setDescription("test content");
        assertThat(tagMapper.insert(tag), is(1));
        System.out.println(tagMapper.selectById(tag.getId()));
        assertThat(tagMapper.deleteById(tag.getId()), is(1));
        assertNull(tagMapper.selectById(tag.getId()));
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(tagMapper.selectById(1));
        Tag updated = new Tag();
        updated.setId(1);
        updated.setTitle("test");
        updated.setDescription("test info");
        updated.setIconSrc("/images/tag/test.jpg");
        assertThat(tagMapper.updateById(updated), is(1));
        Tag tag = tagMapper.selectById(1);
        assertThat(tag.toString(), is(updated.toString()));
        System.out.println(updated);
    }

    @Test
    @Transactional
    public void selectById() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTitle("java");
        tag.setDescription("Java (not to be confused with JavaScript or JScript or JS) is a general-purpose object-oriented programming language designed to be used in conjunction with the Java Virtual Machine (JVM). \"Java platform\" is the name for a computing system that has installed tools for developing and running Java programs. Use this tag for questions referring to the Java programming language or Java platform tools.");
        assertThat(tagMapper.selectById(1).toString(), is(tag.toString()));
        System.out.println(tag);
    }

    @Test
    @Transactional
    public void selectByTitle() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTitle("java");
        tag.setDescription("Java (not to be confused with JavaScript or JScript or JS) is a general-purpose object-oriented programming language designed to be used in conjunction with the Java Virtual Machine (JVM). \"Java platform\" is the name for a computing system that has installed tools for developing and running Java programs. Use this tag for questions referring to the Java programming language or Java platform tools.");
        assertThat(tagMapper.selectByTitle("java").toString(), is(tag.toString()));
        System.out.println(tag);
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        List<Tag> tags = tagMapper.selectByQuestionId(1);
        assertThat(tags.size(), is(2));
        System.out.println(tags);
    }

    @Test
    @Transactional
    public void selectAll() {
        List<Tag> tags = tagMapper.selectAll();
        assertThat(tags.size(), is(6));
        System.out.println(tags);
    }
}