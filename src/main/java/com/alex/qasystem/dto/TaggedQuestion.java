package com.alex.qasystem.dto;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.Tag;

import java.util.List;

public class TaggedQuestion {
    private Tag tag;
    private List<Question> questions;
    public TaggedQuestion(Tag tag, List<Question> questions) {
        this.tag = tag;
        this.questions = questions;
    }
    public Tag getTag() {return tag;}
    public List<Question> getQuestions() {return questions;}
}
