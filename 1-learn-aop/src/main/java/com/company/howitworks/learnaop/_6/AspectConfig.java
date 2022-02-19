package com.company.howitworks.learnaop._6;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration(proxyBeanMethods = false)
public class AspectConfig {

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public PointcutAdvisor publishResultPointcutAdvisor(ApplicationEventPublisher eventPublisher) {
    return new DefaultPointcutAdvisor(new PublishResultMethodPointcut(),
        new PublishResultAfterReturnAdvice(eventPublisher));
  }

//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public DefaultBeanFactoryPointcutAdvisor publishResultPointcutAdvisor() {
//        final DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
//        advisor.setPointcut(new PublishResultMethodPointcut());
//        advisor.setAdviceBeanName(PUBLISH_RESULT_ADVICE_BEAN_NAME);
//        return advisor;
//    }
//
//    @Bean(name = PUBLISH_RESULT_ADVICE_BEAN_NAME)
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public PublishResultAfterReturnAdvice publishResultAfterReturnAdvice(ApplicationEventPublisher eventPublisher) {
//        return new PublishResultAfterReturnAdvice(eventPublisher);
//    }
}
