package com.alex.qasystem.aspect;

import com.alex.qasystem.dao.AnswerMapper;
import com.alex.qasystem.dao.MedalMapper;
import com.alex.qasystem.dao.MedalRecordMapper;
import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.MedalRecord;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.MedalService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class MedalAspect {

    private MedalService medalService;
    private MedalRecordMapper medalRecordMapper;
    private QuestionMapper questionMapper;
    private AnswerMapper answerMapper;


    @Autowired
    public void setMedalService(MedalService medalService) {
        this.medalService = medalService;
    }

    @Autowired
    public void setMedalRecordMapper(MedalRecordMapper medalRecordMapper) {
        this.medalRecordMapper = medalRecordMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.UserService.login(..)),",
            returning = "userAuthExecution")
    public MedalRecord updateOneHundredReputationMedal(JoinPoint joinPoint, UserAuthExecution userAuthExecution) {
        User user = userAuthExecution.getUser();
        if(user == null) {
            return null;
        }
        if(user.getReputation() >= 100 && medalRecordMapper.selectByUserIdAndMedalId(user.getId(), 2) == null) {
            return medalService.awardOneHundreadReputationMedal(user.getId());
        }
        return null;
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.QuestionService.addQuestion(..))",
            returning = "question")
    public MedalRecord updateFirstQuestionMedal(JoinPoint joinPoint, Question question) {
        if(question == null) { return null; }
        List<Question> questions = questionMapper.selectByUserId(question.getUserId());
        if(questions.size() >= 1 && medalRecordMapper.selectByUserIdAndMedalId(question.getUserId(), 1) == null) {
            return medalService.awardFirstQuestionMedal(question.getUserId());
        }
        return null;
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.AnswerService.addAnswer(..))",
            returning = "answer")
    public MedalRecord updateWillingToHelpMedal(JoinPoint joinPoint, Answer answer) {
        if (answer == null ) { return null; }
        List<Answer> answers = answerMapper.selectByUserId(answer.getUserId());
        if (answers.size() >= 10 && medalRecordMapper.selectByUserIdAndMedalId(answer.getUserId(), 3) == null) {
            return medalService.awardWillingToHelpMedal(answer.getUserId());
        }
        return null;
    }


}
