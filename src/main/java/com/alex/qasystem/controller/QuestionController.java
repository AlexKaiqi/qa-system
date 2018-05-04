package com.alex.qasystem.controller;

import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.dto.TaggedQuestion;
import com.alex.qasystem.entity.*;
import com.alex.qasystem.service.QuestionService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

@Controller
public class QuestionController {

    private QuestionService questionService;
    private UserService userService;
    private TagMapper tagMapper;
    private static final Pattern TAG_PATTERN = Pattern.compile("\\[([\\w-_ ]+)\\]");

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

    @GetMapping("questions/tagged/{tags}")
    public String queryQuestionsByTags(@PathVariable String tags,
                                       ModelMap modelMap) {
        tags = tags.replaceAll("\\+", "|");
        List<Question> questions = questionService.queryQuestionByTagRegexp(tags);
        modelMap.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping("questions/search")
    public String queryQuestionsByTitle(@RequestParam("keywords") String keywords,
                                        ModelMap modelMap) {
        // keywords = keywords.replaceAll("\\+", "|");
        List<Question> questions = questionService.queryQuestionByTitleRegexp(keywords);
        modelMap.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping("questions/{questionId}/{questionTitle}")
    public String getQuestion(@PathVariable Integer questionId,
                              @PathVariable(required = false) String questionTitle,
                              ModelMap modelMap) {
        Question question = questionService.getQuestionById(questionId);
        modelMap.addAttribute("question", question);
        return "question";
    }

    @GetMapping("questions/ask")
    public String askQuestion() {
        return "ask-question";
    }

    @PostMapping("questions/ask")
    @ResponseBody
    public Map<String, Object> addQuestion(@RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam("tags[]") List<String> tags,
                                           @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        Question question;
        try {
            question = questionService.addQuestion(user.getId(), title, description, tags);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "can not add question");
            return map;
        }
        map.put("success", true);
        map.put("question", question);
        return map;
    }

    @PostMapping("questions/{questionId}/answer")
    @ResponseBody
    public Map<String, Object> addAnswer(@PathVariable Integer questionId,
                                         @RequestParam String content,
                                         @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        Answer answer;
        try {
            answer = questionService.addAnswer(user.getId(), questionId, content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "can not add answer");
            return map;
        }
        map.put("success", true);
        map.put("answer", answer);
        return map;
    }

    @PostMapping("questions/{questionId}/comment")
    @ResponseBody
    public Map<String, Object> addQuestionComment(@PathVariable Integer questionId,
                                                  @RequestParam String content,
                                                  @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        QuestionComment questionComment;
        try {
            questionComment = questionService.addQuestionComment(user.getId(), questionId, content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "can not add question comment");
            return map;
        }
        map.put("success", true);
        map.put("questionComment", questionComment);
        return map;
    }

    @PostMapping("/answers/{answerId}/comment")
    @ResponseBody
    public Map<String, Object> addAnswerComment(@PathVariable Integer answerId,
                                                @RequestParam String content,
                                                @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        AnswerComment answerComment;
        try {
            answerComment = questionService.addAnswerComment(user.getId(), answerId, content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "can not add answer comment");
            return map;
        }
        map.put("success", true);
        map.put("answerComment", answerComment);
        return map;
    }

    @PostMapping("questions/{questionId}/approval")
    @ResponseBody
    public Map<String, Object> addQuestionApproval(@PathVariable Integer questionId,
                                                   @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        questionService.addQuestionApproval(user.getId(), questionId, 0);
        map.put("success", true);
        map.put("message", "approval succeed");
        return map;
    }

    @PostMapping("answers/{answerId}/approval")
    @ResponseBody
    public Map<String, Object> addAnswerApproval(@PathVariable Integer answerId,
                                                 @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        questionService.addAnswerApproval(user.getId(), answerId, 0);
        map.put("success", true);
        map.put("message", "approval succeed");
        return map;
    }

    @PostMapping("questions/{questionId}/disapproval")
    @ResponseBody
    public Map<String, Object> addQuestionDisapproval(@PathVariable Integer questionId,
                                                      @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        questionService.addQuestionApproval(user.getId(), questionId, 1);
        map.put("success", true);
        map.put("message", "approval succeed");
        return map;
    }

    @PostMapping("answers/{answerId}/disapproval")
    @ResponseBody
    public Map<String, Object> addAnswerDisapproval(@PathVariable Integer answerId,
                                                    @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        questionService.addAnswerApproval(user.getId(), answerId, 1);
        map.put("success", true);
        map.put("message", "approval succeed");
        return map;
    }

    @GetMapping("questions/tagged")
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

    @GetMapping("/questions")
    public String getFilteredQuestions(@RequestParam(required = false) List<String> tags,
                                       @RequestParam(required = false) Date startDate,
                                       @RequestParam(required = false) Date endDate,
                                       @RequestParam(required = false) String title,
                                       ModelMap modelMap) {

        modelMap.addAttribute("questions", questionService.queryQuestionByTitleRegexp(".*"));
        return "questions";
    }
}
