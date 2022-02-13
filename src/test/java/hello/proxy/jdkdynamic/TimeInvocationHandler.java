package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target; // 동적 프록시가 호출할 대상

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    @Override                  // 프록시의 어떤 메서드가 실행될지 넘어온다. -> target의 method 호출된다!!!
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // target 인스턴스의 메서드 실행 args는 메서드 호출시에 넘겨줄 인수
        Object result = method.invoke(target, args); // 메서드 호출 동적임

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}",resultTime);
        return null;
    }
}
