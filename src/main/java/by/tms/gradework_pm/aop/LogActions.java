package by.tms.gradework_pm.aop;

import by.tms.gradework_pm.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Aspect
public class LogActions {

    private static final Logger logger = LoggerFactory.getLogger(LogActions.class.getName());

    @After("@annotation(by.tms.gradework_pm.aop.LogActionsAspect)")
    public void throwingExceptionLogger(JoinPoint joinPoint){

        int index = joinPoint.toString().lastIndexOf('.');

        String currentMethod = joinPoint.toString()
                .substring(index + 1, joinPoint.toString().length()-1);
        User currentUser = SecurityUtil.getCurrentUser();

        logger.warn(currentMethod + " was called by " + currentUser.getUsername() + " at " + Instant.now());
    }
}
