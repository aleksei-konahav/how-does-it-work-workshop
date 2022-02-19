package com.company.howitworks.learnaop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.company.howitworks.learnaop._6.PublishResult;
import com.company.howitworks.learnaop._6.PublishResultAfterReturnAdvice;
import com.company.howitworks.learnaop._6.PublishResultMethodPointcut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
public class PublishResultAspectTest {
    @Mock
    private ApplicationEventPublisher eventPublisher;
    private Advisor advisor;

    @BeforeEach
    void beforeEach() {
        this.advisor = new DefaultPointcutAdvisor(
            new PublishResultMethodPointcut(),
            new PublishResultAfterReturnAdvice(eventPublisher)
        );
    }

    @Test
    void shouldFailProxyCreationWhenPublishResultAnnotationOnVoidMethod() {
        class TestClass {
            @PublishResult
            void test() {}
        }

        final ProxyFactory proxyFactory = new ProxyFactory(new TestClass());
        proxyFactory.addAdvisor(this.advisor);

        assertThatThrownBy(proxyFactory::getProxy)
            .isInstanceOf(AopConfigException.class)
            .hasCauseInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldPublishEventWithMethodResultWhenMethodAnnotatedWithPublishResultAnnotation() {
        final Object object = new Object();
        class TestClass {
            @PublishResult
            Object test() {
                return object;
            }
        }

        final ProxyFactory proxyFactory = new ProxyFactory(new TestClass());
        proxyFactory.addAdvisor(this.advisor);
        final TestClass proxy = (TestClass) proxyFactory.getProxy();

        final Object result = proxy.test();

        assertThat(result).isSameAs(object);
        verify(eventPublisher).publishEvent(eq(object));
    }

    @Test
    void shouldNotPublishEventWithMethodResultWhenMethodAnnotatedWithPublishResultAnnotationThrowsException() {
        class TestClass {
            @PublishResult
            Object test() {
                throw new RuntimeException("o_O");
            }
        }

        final ProxyFactory proxyFactory = new ProxyFactory(new TestClass());
        proxyFactory.addAdvisor(this.advisor);
        final TestClass proxy = (TestClass) proxyFactory.getProxy();

        assertThatThrownBy(proxy::test)
            .isInstanceOf(RuntimeException.class);
        verify(eventPublisher, never()).publishEvent(any(Object.class));
    }

}
