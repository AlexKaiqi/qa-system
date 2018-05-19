package com.alex.qasystem.dto;

import com.alex.qasystem.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.List;

public class UserAccountInfo {
    private User user;
    private List<Question> questions;
    private List<Answer> answers;
    private List<MedalRecord> medalRecords;
    private List<Question> subscribedQuestions;
    private List<User> subscribedUsers;
    private List<Message> messages;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<MedalRecord> getMedalRecords() {
        return medalRecords;
    }

    public void setMedalRecords(List<MedalRecord> medalRecords) {
        this.medalRecords = medalRecords;
    }

    public List<Question> getSubscribedQuestions() {
        return subscribedQuestions;
    }

    public void setSubscribedQuestions(List<Question> subscribedQuestions) {
        this.subscribedQuestions = subscribedQuestions;
    }

    public List<User> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<User> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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
