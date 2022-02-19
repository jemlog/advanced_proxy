package hello.proxy.myTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CGLIBTest {

    @Test
    void cglibTesting()
    {
        MyClass target = new MyClass();
        // cglib는 enhancer 사용
        Enhancer enhancer = new Enhancer();
        // 어떤 superclass 사용할지 명시
        enhancer.setSuperclass(MyClass.class);
        enhancer.setCallback(new MyMethodInterceptor(target));
        // 프록시 생성
        MyClass proxy = (MyClass) enhancer.create();
        log.info("proxy class={}",proxy.getClass());
    }
}
