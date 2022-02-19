package com.company.howitworks.learnaop._2;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@UtilityClass
@Slf4j
public class ExecutionLogger {

    public static void logExecution(String method, String arguments, Runnable code) {
        final StopWatch stopWatch = new StopWatch();
        try {
            log.info("Start execution of [{}] with arguments: {}", method, arguments);
            stopWatch.start();
            code.run();
            stopWatch.stop();
            log.info("Finish execution of [{}] (running {} ns)", method, stopWatch.getTotalTimeNanos());
        } catch (RuntimeException ex) {
            log.info("Fail execution of [{}] (running {} ns)", method, stopWatch.getTotalTimeNanos(), ex);
            throw ex;
        }
    }
}
