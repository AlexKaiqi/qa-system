package com.alex.qasystem.service;

import com.alex.qasystem.entity.Bookmark;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface BookmarkService {

    List<Question> selectBookmarkedQuestionByUserId(Integer userId);

    Bookmark addBookmark(User user, Integer questionId);

    Bookmark deleteBookmarkByQuestionId(User user, Integer questionId) throws NotFoundException, AuthException;

}
