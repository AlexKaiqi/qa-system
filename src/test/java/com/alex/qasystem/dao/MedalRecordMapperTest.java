package com.alex.qasystem.dao;

import com.alex.qasystem.entity.MedalRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedalRecordMapperTest {

    @Autowired
    private MedalRecordMapper medalRecordMapper;
    @Autowired
    private MedalMapper medalMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49

    @Test
    @Transactional
    public void insert() {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setMedalId(5);
        medalRecord.setUserId(2);
        medalRecord.setAwardTime(new Date(DATETIME));
        medalRecord.setMedal(medalMapper.selectById(5));
        assertThat(medalRecordMapper.insert(medalRecord), is(1));
        assertThat(medalRecordMapper.selectById(medalRecord.getId()).toString(), is(medalRecord.toString()));
        System.out.println(medalRecord);
    }

    @Test
    @Transactional
    public void deleteById() {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setMedalId(5);
        medalRecord.setUserId(2);
        medalRecord.setAwardTime(new Date(DATETIME));
        medalRecord.setMedal(medalMapper.selectById(5));
        assertThat(medalRecordMapper.insert(medalRecord), is(1));
        assertThat(medalRecordMapper.deleteById(medalRecord.getId()), is(1));
        assertNull(medalRecordMapper.selectById(medalRecord.getId()));
    }

    @Test
    @Transactional
    public void selectById() {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setId(1);
        medalRecord.setMedalId(1);
        medalRecord.setUserId(1);
        medalRecord.setAwardTime(new Date(DATETIME));
        medalRecord.setMedal(medalMapper.selectById(1));
        assertThat(medalRecordMapper.selectById(1).toString(), is(medalRecord.toString()));
        System.out.println(medalRecord);
    }

    @Test
    @Transactional
    public void selectByUserId() {
        List<MedalRecord> medalRecords = medalRecordMapper.selectByUserId(1);
        assertThat(medalRecords.size(), is(2));
        System.out.println(medalRecords);
    }
}