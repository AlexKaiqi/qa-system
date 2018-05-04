package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Medal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedalMapperTest {

    @Autowired
    private MedalMapper medalMapper;

    @Test
    @Transactional
    public void insert() {
        Medal medal = new Medal();
        medal.setTitle("new medal");
        medal.setDescription("new test medal description");
        medal.setIconSrc("/images/medals/test.jpg");
        assertThat(medalMapper.insert(medal), is(1));
        assertThat(medalMapper.selectById(medal.getId()).toString(), is(medal.toString()));
        System.out.println(medal);
    }

    @Test
    @Transactional
    public void deleteById() {
        Medal medal = new Medal();
        medal.setTitle("new medal");
        medal.setDescription("new test medal description");
        medal.setIconSrc("/images/medals/test.jpg");
        assertThat(medalMapper.insert(medal), is(1));
        assertThat(medalMapper.deleteById(medal.getId()), is(1));
        assertNull(medalMapper.selectById(medal.getId()));
        System.out.println(medal);
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(medalMapper.selectById(1));
        Medal updated = new Medal();
        updated.setId(1);
        updated.setTitle("new medal");
        updated.setDescription("new test medal description");
        updated.setIconSrc("/images/medals/test.jpg");
        assertThat(medalMapper.updateById(updated), is(1));
        assertThat(medalMapper.selectById(1).toString(), is(updated.toString()));
        System.out.println(updated);
    }

    @Test
    @Transactional
    public void selectById() {
        Medal medal = medalMapper.selectById(1);
        assertThat(medal.getId(), is(1));
        assertThat(medal.getTitle(), is("小试牛刀"));
        assertThat(medal.getDescription(), is(  "恭喜完成首个提问"));
        assertThat(medal.getIconSrc(), is("/images/medals/first-quetion.jpg"));
        System.out.println(medal);
    }
}