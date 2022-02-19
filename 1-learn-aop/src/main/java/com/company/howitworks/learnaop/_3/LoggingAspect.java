package com.company.howitworks.learnaop._3;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.company.howitworks.learnaop._3.CongratulationService.*(..))")
    public void congratulationServiceMethods() {}

    @Around("congratulationServiceMethods()")
    public Object logMethodExecution(ProceedingJoinPoint pjp) throws Throwable {
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final Method method = signature.getMethod();
        final StopWatch stopWatch = new StopWatch();
        try {
            final String arguments = IntStream.iterate(0, i -> i + 1)
                .limit(Math.min(signature.getParameterNames().length, pjp.getArgs().length))
                .mapToObj(i -> signature.getParameterNames()[i] + "=" + pjp.getArgs()[i])
                .collect(Collectors.joining(","));
            log.info("Start execution of {} with arguments: {}", method, arguments);
            stopWatch.start();
            final Object result = pjp.proceed();
            stopWatch.stop();
            log.info("Finish execution of {} (running {} ns)", method, stopWatch.getTotalTimeNanos());
            return result;
        } catch (Exception ex) {
            log.info("Fail execution of {} (running {} ns)", method, stopWatch.getTotalTimeNanos(), ex);
            throw ex;
        }
    }
}
