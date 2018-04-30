package com.alex.qasystem.dto;

import com.alex.qasystem.entity.*;

import java.util.List;

public class UserAccountInfo {
    private User user;
    private List<Question> questions;
    private List<Answer> answers;
    private List<MedalRecord> medalRecords;
    private Integer approvals;
    private Integer disapprovals;
}
