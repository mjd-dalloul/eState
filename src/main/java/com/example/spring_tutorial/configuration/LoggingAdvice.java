package com.example.spring_tutorial.configuration;

import com.example.spring_tutorial.domain.dto.auth_dto.CustomUserSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {
    private final Logger logger = LogManager.getLogger();

    @Pointcut(value = "execution(* com.example.spring_tutorial.api.*.*(..) )")
    public void myPointcut() {
        logger.info("Executing the function");
    }

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Object[] parameters = proceedingJoinPoint.getArgs();
        logger.info("Method invoked " + className + " -> "
                + methodName + "args: "
                + mapper.writeValueAsString(parameters));
        if (SecurityContextHolder
                .getContext() == SecurityContextHolder.createEmptyContext() || SecurityContextHolder
                .getContext()
                .getAuthentication() instanceof AnonymousAuthenticationToken) {
            logger.info("anonymous user is accessing a public api");

        } else {
            logger.info("request are coming from " + ((CustomUserSecurity) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUser().getFullName());
        }
        Object ret = proceedingJoinPoint.proceed(parameters);
        logger.info(className + " -> " + methodName + " Response : " + mapper.writeValueAsString(ret));
        return ret;
    }

}
