package com.company.howitworks.learnaop._5;

import java.util.concurrent.locks.Lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExclusiveLockAspect {
    private final Lock lock;

    @Around("@annotation(com.company.howitworks.learnaop._5.ExclusiveLock)")
    public Object exclusiveLockAspect(ProceedingJoinPoint pjp) throws Throwable {
        if (lock.tryLock()) {
            try {
                return pjp.proceed();
            } finally {
                lock.unlock();
            }
        } else {
            log.warn("Lock was not obtained!");
            return null;
        }
    }
}
