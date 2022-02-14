package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // 메서드 내부는 어드바이스 로직
    // around는 포인트컷
    // 어드바이져를 만들어내는 로직!
    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable
    {
        TraceStatus status = null;
        // joinPoint.getTarget() // 실제 호출 대상
        // joinPoint.getArgs() // 전달인자
        // joinPoint.getSignature() // join point 시그니처
        try {
            String message = joinPoint.getSignature().toShortString();
            status =  logTrace.begin(message);

            // 로직 호출
            Object result = joinPoint.proceed(); // target 호출
            logTrace.end(status);
            return result;
        }
        catch (Exception e)
        {
            logTrace.exception(status,e);
            throw e;
        }
    }

}
