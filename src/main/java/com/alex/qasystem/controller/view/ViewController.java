package com.alex.qasystem.controller.view;

import com.alex.qasystem.dao.AnswerMapper;
import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.dto.TaggedQuestion;
import com.alex.qasystem.entity.*;
import com.alex.qasystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {
    private QuestionService questionService;
    private UserService userService;
    private BookmarkService bookmarkService;
    private QuestionSubscriptionService questionSubscriptionService;
    private UserSubscriptionService userSubscriptionService;
    private MessageService messageService;
    private TagMapper tagMapper;
    private QuestionMapper questionMapper;
    private AnswerMapper answerMapper;

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookmarkService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @Autowired
    public void setQuestionSubscriptionService(QuestionSubscriptionService questionSubscriptionService) {
        this.questionSubscriptionService = questionSubscriptionService;
    }

    @Autowired
    public void setUserSubscriptionService(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/questions/tagged/{tags}")
    public String queryQuestionsByTags(@PathVariable String tags,
                                       ModelMap modelMap) {
        tags = tags.replaceAll("\\+", "|");
        List<Question> questions = questionService.queryQuestionByTagRegexp(tags);
        modelMap.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping("/questions/search")
    public String queryQuestionsByTitle(@RequestParam("keywords") String keywords,
                                        ModelMap modelMap) {
        List<Question> questions = questionService.queryQuestionByTitleRegexp(keywords);
        modelMap.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping("/questions/{questionId}/{questionTitle}")
    public String getQuestion(@PathVariable Integer questionId,
                              @PathVariable(required = false) String questionTitle,
                              ModelMap modelMap) {
        Question question = questionService.getQuestionById(questionId);
        modelMap.addAttribute("question", question);
        String tagString = "";
        for (Tag tag: question.getTags()) {
            tagString += tag.getTitle()+"+";
        }
        tagString = tagString.substring(0, tagString.length() - 1);
        List<Question> relativeQuestions = questionService.queryQuestionByTagRegexp(tagString.replaceAll("\\+","|"));
        modelMap.addAttribute("tagString", tagString);
        modelMap.addAttribute("relativeQuestions", relativeQuestions);
        return "question";
    }

    @GetMapping("/questions/ask")
    public String askQuestion() {
        return "ask-question";
    }

    @GetMapping("/questions/tagged")
    public String getTaggedQuestions(ModelMap modelMap) {
        List<Tag> tags = tagMapper.selectAll();

        List<TaggedQuestion> taggedQuestons = new ArrayList<>();
        for (Tag tag : tags) {
            List<Question> questions = questionService.queryQuestionByTagRegexp(tag.getTitle());
            taggedQuestons.add(new TaggedQuestion(tag, questions));
        }
        modelMap.addAttribute("taggedQuestions", taggedQuestons);
        return "tags";
    }

    @GetMapping("/questions/all")
    public String getFilteredQuestions(@RequestParam(required = false) List<String> tags,
                                       @RequestParam(required = false) Date startDate,
                                       @RequestParam(required = false) Date endDate,
                                       @RequestParam(required = false) String title,
                                       ModelMap modelMap) {

        modelMap.addAttribute("questions", questionService.queryQuestionByTitleRegexp(".*"));
        return "questions";
    }

    @GetMapping("/user/account")
    public String account(@RequestParam String token,
                          ModelMap modelMap) {
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            return "login";
        }
        List<Message> messages = messageService.getMessagesByReceiverId(user.getId());
        List<Message> unreadMessages = messages.stream().filter(x->!x.isActive()).collect(Collectors.toList());
        List<Message> readMessages = messages.stream().filter(Message::isActive).collect(Collectors.toList());
        List<Question> subscribedQuestions = questionSubscriptionService.getSubscribedQuestionsByUserId(user.getId());
        List<User> subscribedUsers = userSubscriptionService.getSubscribedUserByUserId(user.getId());
        List<Question> bookmarks = bookmarkService.selectBookmarkedQuestionByUserId(user.getId());
        List<Question> myQuestions = questionMapper.selectByUserId(user.getId());
        List<Answer> myAnswers = answerMapper.selectByUserId(user.getId());
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("messages", messages);
        modelMap.addAttribute("unreadMessages", unreadMessages);
        modelMap.addAttribute("readMessages", readMessages);
        modelMap.addAttribute("subscribedQuestions", subscribedQuestions);
        modelMap.addAttribute("subscribedUsers", subscribedUsers);
        modelMap.addAttribute("bookmarks", bookmarks);
        modelMap.addAttribute("myQuestions", myQuestions);
        modelMap.addAttribute("myAnswers", myAnswers);
        return "account";
    }

    @GetMapping("/user/sign-in")
    public String signIn() {
        return "login";
    }

    @GetMapping("/user/sign-up")
    public String signUp() {
        return "login";
    }
}
