package hello.proxy.config.v2_proxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns; //메서드명이 특정 필터일때만 넘긴다.
    }

    @Override                          // 실제 impl의 method 정보!
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        // save, request,reque*, *est
        if(!PatternMatchUtils.simpleMatch(patterns, methodName))
        {
            return method.invoke(target,args); // 공통 로직만 실행 안하고 실제 객체로는 넘겨줘야 함
        }

        TraceStatus status = null;
        try {
          String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status =  logTrace.begin(message);
            // 로직 호출
            Object result = method.invoke(target, args);
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
