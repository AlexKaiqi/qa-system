package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.BookmarkMapper;
import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.entity.Bookmark;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private BookmarkMapper bookmarkMapper;
    private QuestionMapper questionMapper;

    @Autowired
    public void setBookmarkMapper(BookmarkMapper bookmarkMapper) {
        this.bookmarkMapper = bookmarkMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }
    @Override
    public List<Question> selectBookmarkedQuestionByUserId(Integer userId) {
        List<Bookmark> bookmarks = bookmarkMapper.selectByUserId(userId);
        List<Question> questions = new ArrayList<>();
        for (Bookmark bookmark: bookmarks) {
            questions.add(questionMapper.selectById(bookmark.getQuestionId()));
        }
        return questions;
    }

    @Override
    public Bookmark addBookmark(Integer userId, Integer questionId) {
        // ����Ƿ��Ѿ��ղ�, �����ֱ�ӷ���.
        List<Bookmark> bookmarks = bookmarkMapper.selectByUserId(userId);
        for (Bookmark bookmark: bookmarks) {
            if (bookmark.getQuestionId().equals(questionId)) {
                return bookmark;
            }
        }
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(userId);
        bookmark.setQuestionId(questionId);
        bookmarkMapper.insert(bookmark);
        return bookmark;
    }

    @Override
    public Bookmark deleteBookmarkById(User user, Integer bookmarkId) {
        Bookmark bookmark = bookmarkMapper.selectById(bookmarkId);
        if(bookmark == null) {
            throw new RuntimeException("�Ҳ����������Ѳ�. bookmarkId: " + bookmarkId);
        }
        if (!bookmark.getUserId().equals(user.getId())) {
            throw new RuntimeException("û��ɾ���ղص�Ȩ��. userId: " + user.getId());
        }
        bookmarkMapper.deleteById(bookmarkId);
        return bookmark;
    }

}