package com.issoft.cinemaapplication.mock;

import lombok.RequiredArgsConstructor;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

@RequiredArgsConstructor
public class MockAnswer implements Answer<Object> {
    private final Object bean;

    @Override
    public Object answer(final InvocationOnMock invocationOnMock) throws Throwable {
        return ReflectionTestUtils
                .invokeMethod(this.bean, invocationOnMock.getMethod().getName(), ((Invocation) invocationOnMock).getRawArguments());
    }
}
