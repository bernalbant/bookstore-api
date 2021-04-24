package com.bookstore.aspect;

import com.bookstore.security.UserPrincipal;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("!test")
public class LoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.bookstore.service.*.save(..))")
    private void forSave() {
    }

    @Pointcut("execution(* com.bookstore.service.Customer*.delete*(..))")
    private void forCustomerDelete() {
    }

    @Pointcut("execution(* com.bookstore.service.Order*.delete*(..))")
    private void forOrderDelete() {
    }

    @Pointcut("execution(* com.bookstore.service.Book*.delete*(..))")
    private void forBookDelete() {
    }

    @AfterReturning(pointcut = "forSave()", returning = "result")
    public void afterSave(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.info(method + " is executed.");
        logger.info(getCurrentUser().getUsername() + " saved the following object: " + result);
    }

    @AfterReturning(pointcut = "forCustomerDelete()", returning = "result")
    public void afterCustomerDelete(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        int id = (int) joinPoint.getArgs()[0];
        logger.info(method + " is executed.");
        logger.info(getCurrentUser().getUsername() + " deleted the customer with following id: " + id);
    }

    @AfterReturning(pointcut = "forOrderDelete()", returning = "result")
    public void afterOrderDelete(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        int id = (int) joinPoint.getArgs()[0];
        logger.info(method + " is executed.");
        logger.info(getCurrentUser().getUsername() + " deleted the order with following id: " + id);
    }

    @AfterReturning(pointcut = "forBookDelete()", returning = "result")
    public void afterBookDelete(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        int id = (int) joinPoint.getArgs()[0];
        logger.info(method + " is executed.");
        logger.info(getCurrentUser().getUsername() + " deleted the book with following id: " + id);
    }

    public UserPrincipal getCurrentUser() {
        UserPrincipal userPrincipal =
                (UserPrincipal) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userPrincipal;
    }
}