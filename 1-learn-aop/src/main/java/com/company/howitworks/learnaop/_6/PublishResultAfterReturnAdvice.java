package com.company.howitworks.learnaop._6;

import java.lang.reflect.Method;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class PublishResultAfterReturnAdvice implements AfterReturningAdvice {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (returnValue != null) {
            eventPublisher.publishEvent(returnValue);
        }
    }
}
