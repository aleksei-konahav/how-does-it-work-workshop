package com.company.howitworks.learnaspectj;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public aspect LoggingAspect {
  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

  pointcut congratulationServicePublicMethods(Object object):
      execution(public * CongratulationService.*(..)) && this(object);

  Object around(Object object): congratulationServicePublicMethods(object) {
    final MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
    final Method method = signature.getMethod();
    final StopWatch stopWatch = new StopWatch();
    try {
      final String arguments = IntStream.iterate(0, i -> i + 1)
          .limit(Math.min(signature.getParameterNames().length, thisJoinPoint.getArgs().length))
          .mapToObj(i -> signature.getParameterNames()[i] + "=" + thisJoinPoint.getArgs()[i])
          .collect(Collectors.joining(","));
      log.info("Start execution of {} with arguments: {}", method, arguments);
      stopWatch.start();
      final Object result = proceed(object);
      stopWatch.stop();
      log.info("Finish execution of {} (running {} ns)", method, stopWatch.getTotalTimeNanos());
      return result;
    } catch (Throwable thr) {
      log.info("Fail execution of {} (running {} ns)", method, stopWatch.getTotalTimeNanos(), thr);
      Rethrower.rethrow(thr);
      throw new IllegalStateException("Should never get here", thr);
    }
  }

  private static class Rethrower {

    public static void rethrow(final Throwable exception) {
      class CheckedExceptionRethrower<T extends Throwable> {
        @SuppressWarnings("unchecked")
        private void rethrow(Throwable exception) throws T {
          throw (T) exception;
        }
      }
      new CheckedExceptionRethrower<RuntimeException>().rethrow(exception);
    }
  }
}
