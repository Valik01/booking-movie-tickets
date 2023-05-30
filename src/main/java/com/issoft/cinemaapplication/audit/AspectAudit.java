package com.issoft.cinemaapplication.audit;

import com.issoft.cinemaapplication.model.Audit;
import com.issoft.cinemaapplication.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class AspectAudit {
    private final AuditService auditService;

    @Pointcut("@annotation(Audit)")
    public void callWithAuditAnnotation() {
    }

    @Around("callWithAuditAnnotation()")
    public Object aroundCall(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Audit audit = new Audit();

        final String args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));

        audit.setMethodName(joinPoint.getSignature().getName());
        audit.setArguments(args);

        try {
            final Object returnValue = joinPoint.proceed();
            audit.setReturnValue(String.valueOf(returnValue));
            return returnValue;
        } catch (final Throwable e) {
            audit.setException(e.getMessage());
            throw e;
        } finally {
            this.auditService.save(audit);
        }
    }
}
