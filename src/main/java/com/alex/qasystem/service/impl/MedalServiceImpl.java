package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.MedalMapper;
import com.alex.qasystem.dao.MedalRecordMapper;
import com.alex.qasystem.entity.Medal;
import com.alex.qasystem.entity.MedalRecord;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.MedalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MedalServiceImpl implements MedalService {
    private MedalMapper medalMapper;
    private MedalRecordMapper medalRecordMapper;

    @Autowired
    public void setMedalMapper(MedalMapper medalMapper) {
        this.medalMapper = medalMapper;
    }

    @Autowired
    public void setMedalRecordMapper(MedalRecordMapper medalRecordMapper) {
        this.medalRecordMapper = medalRecordMapper;
    }

    @Override
    public List<MedalRecord> getMedalsByUserId(Integer userId) {
        return medalRecordMapper.selectByUserId(userId);
    }

    @Override
    public MedalRecord awardFirstQuestionMedal(Integer userId) {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setUserId(userId);
        medalRecord.setAwardTime(new Date());
        medalRecord.setMedalId(1);
        medalRecord.setMedal(medalMapper.selectById(1));
        medalRecordMapper.insert(medalRecord);
        return medalRecord;
    }

    @Override
    public MedalRecord awardOneHundreadReputationMedal(Integer userId) {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setUserId(userId);
        medalRecord.setAwardTime(new Date());
        medalRecord.setMedalId(2);
        medalRecord.setMedal(medalMapper.selectById(2));
        medalRecordMapper.insert(medalRecord);
        return medalRecord;
    }

    @Override
    public MedalRecord awardTopUserMedal(Integer userId) {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setUserId(userId);
        medalRecord.setAwardTime(new Date());
        medalRecord.setMedalId(4);
        medalRecord.setMedal(medalMapper.selectById(4));
        medalRecordMapper.insert(medalRecord);
        return medalRecord;
    }

    @Override
    public MedalRecord awardWillingToHelpMedal(Integer userId) {
        MedalRecord medalRecord = new MedalRecord();
        medalRecord.setUserId(userId);
        medalRecord.setAwardTime(new Date());
        medalRecord.setMedalId(3);
        medalRecord.setMedal(medalMapper.selectById(3));
        medalRecordMapper.insert(medalRecord);
        return medalRecord;
    }


}
