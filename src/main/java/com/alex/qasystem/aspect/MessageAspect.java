package com.alex.qasystem.aspect;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.service.MessageService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessageAspect {
    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.AnswerService.addAnswer(..))",
            returning = "answer")
    public void addQuestionHasNewAnswerMessage(JoinPoint joinPoint, Answer answer) {
        messageService.addQuestionHasNewAnswerMessage(answer);
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.AnswerService.addAnswer(..))",
            returning = "answer")
    public void addUserPostNewAnswerMessage(JoinPoint joinPoint, Answer answer) {
        messageService.addUserPostNewAnswerMessage(answer);
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.QuestionService.addQuestion(..))",
            returning = "question")
    public void addUserPostNewAnswerMessage(JoinPoint joinPoint, Question question) {
        messageService.addUserPostNewQuestionMessage(question);
    }

}
