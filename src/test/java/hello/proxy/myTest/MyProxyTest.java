package hello.proxy.myTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.ref.PhantomReference;

@Slf4j
public class MyProxyTest {

    @Test
    @DisplayName("내가 만든 advise가 잘 동작하는지 확인해보자")
    void ProxyTest()
    {
        MyInterface target = new MyClass();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new MyAdvice());
        // setProxyTargetClass는 인터페이스 기반이라도 CGLIB로 생성하게 해준다.
        proxyFactory.setProxyTargetClass(true);
        MyInterface proxy = (MyInterface) proxyFactory.getProxy();
        String result = proxy.methodB();
        log.info("어떤 클래스가 만들어졌을까={}", proxy.getClass());
    }

    @Test
    @DisplayName("내가 만든 advisor가 잘 동작하는지 확인해보자")
    void AdvisorTest()
    {
        // target 생성
        MyInterface target = new MyClass();

        // 프록시팩토리 생성 : 이 과정에서 target을 집어넣고, target의 정보를 기준으로 JDK와 CGLIB 중에 선택한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 메서드 이름을 기준으로 판별하는 포인트컷 생성
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

        // methodA라는 이름을 가진 메서드만 어드바이스 지정
        pointcut.setMappedNames("methodA");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new MyAdvice());
        proxyFactory.addAdvisor(advisor);

        // 프록시 생성
        MyInterface proxy = (MyInterface) proxyFactory.getProxy();

        proxy.methodA();
        proxy.methodB();
    }
}
