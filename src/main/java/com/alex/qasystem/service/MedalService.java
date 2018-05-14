package com.alex.qasystem.service;

import com.alex.qasystem.entity.MedalRecord;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;

import java.util.List;

public interface MedalService {

    List<MedalRecord> getMedalsByUserId(Integer userId);

    MedalRecord awardFirstQuestionMedal(Integer userId);

    MedalRecord awardOneHundreadReputationMedal(Integer userId);

    MedalRecord awardTopUserMedal(Integer userId);

    MedalRecord awardWillingToHelpMedal(Integer userId);

}
