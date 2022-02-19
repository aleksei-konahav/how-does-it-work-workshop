package com.company.howitworks.learnaop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;

import com.company.howitworks.learnaop._5.AspectConfig;
import com.company.howitworks.learnaop._5.CongratulationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CongratulationService.class, AspectConfig.class, ExclusiveLockAspectTest_2.Config.class})
class ExclusiveLockAspectTest_2 {
    @MockBean
    private Lock lock;

    @Autowired
    private CongratulationService congratulationService;

    @Test
    void shouldNotProceedWithJoinPointWhenCanNotLock() {
        when(lock.tryLock()).thenReturn(false);

        final String name = congratulationService.sendGiftTo("name");

        assertThat(name).isNull();
        verify(lock).tryLock();
    }

    @TestConfiguration
    @EnableAspectJAutoProxy
    static class Config {

    }

}