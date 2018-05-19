package com.alex.qasystem.dto;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaggedQuestion {
    private Tag tag;
    private List<Question> questions;

    public TaggedQuestion(Tag tag, List<Question> questions) {
        this.tag = tag;
        this.questions = questions;
    }

    public Tag getTag() {
        return tag;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "cannot parse object as json" + this.hashCode();
    }
}
