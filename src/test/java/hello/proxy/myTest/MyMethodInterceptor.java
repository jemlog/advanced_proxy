package hello.proxy.myTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class MyMethodInterceptor implements MethodInterceptor {

    private final Object target;

    public MyMethodInterceptor(Object target) {
        this.target = target;
    }

    /**
     *
     * @param o cglib 적용된 객체
     * @param method  프록시에서 호출한 메서드
     * @param args  메서드를 선언하면서 전달할 인수
     * @param methodProxy   메서드 호출에 사용 -> methodProxy의 성능이 조금 더 좋다고 하니 methodProxy 사용하자.
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("호출!");
        Object result = methodProxy.invoke(target, args);
        return result;
    }
}
