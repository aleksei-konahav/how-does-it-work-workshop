package com.company.howitworks.learnaop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;

import com.company.howitworks.learnaop._5.ExclusiveLockAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExclusiveLockAspectTest {
    @Mock
    private Lock lock;
    @Mock
    private ProceedingJoinPoint pjp;

    @InjectMocks
    private ExclusiveLockAspect aspect;

    @AfterEach
    void afterEach() {
        Mockito.verifyNoMoreInteractions(lock, pjp);
        Mockito.reset(lock, pjp);
    }

    @Test
    void shouldNotProceedWithJoinPointWhenCanNotLock() throws Throwable {
        when(lock.tryLock()).thenReturn(false);

        final Object result = aspect.exclusiveLockAspect(pjp);

        assertThat(result).isNull();
        verify(lock).tryLock();
    }

}