package com.alex.qasystem.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alex.qasystem.dao.AuthTokenMapper;
import com.alex.qasystem.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Alex
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private AuthTokenMapper authTokenMapper;

    @Autowired
    public void setAuthTokenMapper(AuthTokenMapper authTokenMapper) {this.authTokenMapper = authTokenMapper;}

    @Scheduled(fixedRate = 1600000, initialDelay = 1600000)
    public void removeExpiredToken() {
        Integer count = authTokenMapper.deleteExpiredToken();
        log.info("Scheduled Task: delete {} rows of expired token {}", count, DATE_FORMAT.format(new Date()));
    }
}