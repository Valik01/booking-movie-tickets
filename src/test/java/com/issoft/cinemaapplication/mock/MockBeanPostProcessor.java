package com.issoft.cinemaapplication.mock;

import org.mockito.Mockito;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order
public class MockBeanPostProcessor implements BeanPostProcessor {
    private final List<String> paths;

    public MockBeanPostProcessor(@Value("${path.mapper}") final String mapperPath,
                                 @Value("${path.repository}") final String repositoryPath,
                                 @Value("${path.service}") final String servicePath) {

        this.paths = List.of(mapperPath, repositoryPath, servicePath);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, final String beanName) throws BeansException {
        final Class<?>[] interfaces = bean.getClass().getInterfaces();
        if (Arrays.stream(interfaces).anyMatch(i -> this.paths.contains(i.getPackageName()))) {

            bean = Mockito.mock(AopUtils.getTargetClass(bean), Mockito
                    .withSettings()
                    .extraInterfaces(interfaces)
                    .defaultAnswer(new MockAnswer(bean)));

            MockBeanTestExecutionListener.mockBeans.add(bean);
        }
        return bean;
    }
}
