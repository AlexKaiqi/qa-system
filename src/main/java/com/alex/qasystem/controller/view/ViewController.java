package com.alex.qasystem.controller.view;

import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.dto.TaggedQuestion;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.QuestionService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ViewController {
    private QuestionService questionService;
    private UserService userService;
    private TagMapper tagMapper;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
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
        modelMap.addAttribute("tagString", tagString);
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
            return "/user/sign-in";
        }
        modelMap.put("user", user);

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
