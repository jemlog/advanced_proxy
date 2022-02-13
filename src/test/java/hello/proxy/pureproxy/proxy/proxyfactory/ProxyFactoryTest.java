package hello.proxy.pureproxy.proxy.proxyfactory;

import hello.proxy.pureproxy.proxy.common.advice.TimeAdvice;
import hello.proxy.pureproxy.proxy.common.service.ConcreteService;
import hello.proxy.pureproxy.proxy.common.service.ServiceImpl;
import hello.proxy.pureproxy.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        // 미리 프록시팩토리 만들때 target 정보 넣는다. 그래서 methodInterceptor에 target이 필요없던 것이다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("target.class={}", target.getClass());
        log.info("proxy.class={}", proxy.getClass());

        proxy.save();

        // 프록시 팩토리를 통해 만들었을때 사용가능!
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // 직접 만들면 적용 안되고 프록시 팩토리 통해서 만들었을때!
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();

        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();


    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        // 미리 프록시팩토리 만들때 target 정보 넣는다. 그래서 methodInterceptor에 target이 필요없던 것이다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("target.class={}", target.getClass());
        log.info("proxy.class={}", proxy.getClass());

        proxy.call();

        // 프록시 팩토리를 통해 만들었을때 사용가능!
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // 직접 만들면 적용 안되고 프록시 팩토리 통해서 만들었을때!
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();

        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();


    }

    @Test
    @DisplayName("ProxyTargetClass를 사용하면 인터페이스가 있어도 CGLIB가 사용된다.")
    void proxyTargetClass() {
        ServiceImpl target = new ServiceImpl();
        // 미리 프록시팩토리 만들때 target 정보 넣는다. 그래서 methodInterceptor에 target이 필요없던 것이다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // targetclass를 기반으로 프록시를 만듬
        // 실무에서도 사용!
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("target.class={}", target.getClass());
        log.info("proxy.class={}", proxy.getClass());

        proxy.save();

        // 프록시 팩토리를 통해 만들었을때 사용가능!
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // 직접 만들면 적용 안되고 프록시 팩토리 통해서 만들었을때!
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();

        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();


    }
}
