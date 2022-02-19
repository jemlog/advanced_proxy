package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {


    @Test
    void dynamicA()
    {
        AInterface target = new AImpl();

        // TimeInvocationHandler는 프록시 로직을 실행해준다. 어떤 객체의 프록시인지 target으로 넣어야함
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        // newProxyInstance : 동적으로 생성이 된다.
        // Proxy : jdk에서 제공하는 프록시 생성 기능
        // 어디에 프록시 생성될지 지정 : Interface
        // interface가 여러개일수 있기에 클래스 배열 -> 어떤 인터페이스 기반으로 만들꺼야
        // handler :  프록시가 호출할 로직
        // 최종이 프록시
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        // 프록시에서 call 호출
        // 1. call 하면 handler의 로직을 실행
        // 2. handler의 invoke 실행
        // 3. invoke를 실행할때 인자인 method로 call을 넘겨준다.
        // 4. target의 call을 실행한다.
        proxy.call();
        log.info("targetClass={}",target.getClass());
        // AInterface 구현해서 만들어짐
        log.info("proxyClass={}",proxy.getClass());
    }
    @Test
    void dynamicB()
    {
        BInterface target = new BImpl();

        // 동적 프록시 호출 로직 설정 -> target은 실제 impl 객체이다.
        // invocationHandler가 공통 로직이다.
        TimeInvocationHandler handler = new TimeInvocationHandler(target); // InvocationHandler와 invoke 메서드는 자바에서 제공

        // newProxyInstance : 동적으로 생성이 된다.
        // Proxy : jdk에서 제공하는 프록시 생성 기능
        // 어디에 프록시 생성될지 지정 : Interface
        // interface가 여러개일수 있기에 클래스 배열 -> 어떤 인터페이스 기반으로 만들꺼야
        // handler :  프록시가 호출할 로직
        // 최종이 프록시 : 아래 코드가 프록시 생성 규칙이다!
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        // 프록시에서 call 호출
        // 1. call 하면 handler의 로직을 실행
        // 2. handler의 invoke 실행
        // 3. invoke를 실행할때 인자인 method로 call을 넘겨준다.
        // 4. target의 call을 실행한다.
        proxy.call();
        log.info("targetClass={}",target.getClass());
        // AInterface 구현해서 만들어짐
        log.info("proxyClass={}",proxy.getClass());
    }
        }
