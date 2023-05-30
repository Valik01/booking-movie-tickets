package com.issoft.cinemaapplication.mock;

import org.mockito.Mockito;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.ArrayList;
import java.util.List;

public class MockBeanTestExecutionListener implements TestExecutionListener {
    public static final List<Object> mockBeans = new ArrayList<>();

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        mockBeans.forEach(Mockito::reset);
    }
}
