package com.company.howitworks.learnaop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;

import com.company.howitworks.learnaop._5.ExclusiveLockAspect;
import com.company.howitworks.learnaop._5.CongratulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

@ExtendWith(MockitoExtension.class)
class ExclusiveLockAspectTest_3 {
    @Mock
    private Lock lock;

    private CongratulationService congratulationService;

    @BeforeEach
    void beforeEach() {
        final CongratulationService target = new CongratulationService();

        final AspectJProxyFactory proxyFactory = new AspectJProxyFactory(target);
        proxyFactory.addAspect(new ExclusiveLockAspect(lock));

        this.congratulationService = proxyFactory.getProxy();
    }

    @Test
    void shouldNotProceedWithJoinPointWhenCanNotLock() {
        when(lock.tryLock()).thenReturn(false);

        final String name = congratulationService.sendGiftTo("name");

        assertThat(name).isNull();
        verify(lock).tryLock();
    }

}