package hello.proxy.myTest;

import hello.proxy.decorator.code.MessageDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JDKProxyTest {


    @Test
    @DisplayName("JDK 동적 프록시가 작동하는지 테스트 해보자")
    void jdkProxyTesting()
    {
        // 인터페이스 기반으로 구체클래스를 만들었다!
        // 이제 프록시는 MyInterface를 기반으로 만들어질 예정
        MyInterface target = new MyClass();
        // invocationHandler를 직접 생성해줘야 한다.
        // target은 참조 객체를 넣어주는것 뿐이다.
        MyInvocationHandler invocationHandler = new MyInvocationHandler(target, new String[]{"methodB"});
        // 프록시를 만들때는 target을 가지고 만드는 것이 아니라, 내가 넣어준 interface 정보를 기반으로 프록시를 만든다.
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(MyInterface.class.getClassLoader(), new Class[]{MyInterface.class}, invocationHandler);
        String methodAResult = proxy.methodA();
        log.info("result={}",methodAResult);
        log.info("proxy class={}",proxy.getClass());
        log.info("getClassLoader={}",MessageDecorator.class.getClassLoader());
        // getClassLoader는 일반적인 jvm의 클래스 로더를 의미하는 것이다. 다른 객체의 클래스 로더 주소값을 비교해도 같다!!
        log.info("same={}",MyInterface.class.getClassLoader() == MessageDecorator.class.getClassLoader());


    }
}
