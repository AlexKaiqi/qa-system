package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Bookmark;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookmarkMapperTest {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Test
    @Transactional
    public void insert() {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(1);
        bookmark.setQuestionId(1);
        assertThat(bookmarkMapper.insert(bookmark), is(1));
        assertThat(bookmarkMapper.selectById(bookmark.getId()).toString(), is(bookmark.toString()));
        System.out.println(bookmark);
    }

    @Test
    @Transactional
    public void deleteById() {
        System.out.println(bookmarkMapper.selectById(1));
        assertThat(bookmarkMapper.deleteById(1), is(1));
        assertNull(bookmarkMapper.selectById(1));
    }

    @Test
    @Transactional
    public void deleteByUserId() {
        System.out.println(bookmarkMapper.selectByUserId(1));
        assertThat(bookmarkMapper.deleteByUserId(1), is(2));
        assertThat(bookmarkMapper.selectByUserId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        System.out.println(bookmarkMapper.selectByQuestionId(1));
        assertThat(bookmarkMapper.deleteByQuestionId(1), is(1));
        assertThat(bookmarkMapper.selectByQuestionId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void selectById() {
        Bookmark bookmark = new Bookmark();
        bookmark.setId(1);
        bookmark.setUserId(1);
        bookmark.setQuestionId(1);
        assertThat(bookmarkMapper.selectById(1).toString(), is(bookmark.toString()));
        System.out.println(bookmark);
    }

    @Test
    @Transactional
    public void selectByUserId() {
        assertThat(bookmarkMapper.selectByUserId(1).size(), is(2));
        System.out.println(bookmarkMapper.selectByUserId(1));
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        assertThat(bookmarkMapper.selectByQuestionId(1).size(), is(1));
        System.out.println(bookmarkMapper.selectByQuestionId(1));
    }
}