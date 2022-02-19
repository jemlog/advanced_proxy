package hello.proxy.myTest;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


@Slf4j
public class MyAdvice implements MethodInterceptor {


    @Override            // 여기에 method 정보, target 정보, 인자 모두 다 들어가있다.
    public Object invoke(MethodInvocation invocation) throws Throwable {

        log.info("내가 설정한 advise 먼저 호출");
        Object result = invocation.proceed();
        log.info("target 실행 후 내가 설정한 advise 다시 실행");
        return result;
    }
}
