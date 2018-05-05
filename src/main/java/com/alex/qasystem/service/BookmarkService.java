package com.alex.qasystem.service;

import com.alex.qasystem.entity.Bookmark;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;

import java.util.List;

public interface BookmarkService {

    List<Question> selectBookmarkedQuestionByUserId(Integer userId);

    Bookmark addBookmark(Integer userId, Integer questionId);

    Bookmark deleteBookmarkById(User user, Integer bookmarkId);

}
