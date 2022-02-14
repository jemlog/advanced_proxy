package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0()
    {
        Hello target = new Hello();

        //공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}",result1);
        // 공통 로직 1 종료

        //공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}",result2);
        //공통 로직 2 종료
    }

    @Test
    void reflection1() throws Exception {

        // 클래스의 메타 정보, 메서드, 필드 등의 정보를 가져오는걸 리플랙션 이라고 한다.
        //클래스 메타 정보 얻음
        // 내부 클래스는 구분을 위해서 $를 앞에 붙여준다.
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA"); // callA라는 메서드
        Object result1 = methodCallA.invoke(target); // target의 인스턴스에 있는 메서드 호출
        log.info("result1={}",result1);
        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB"); // callB라는 메서드
        Object result2 = methodCallB.invoke(target); // target의 인스턴스에 있는 메서드 호출
        log.info("result2={}",result2);


    }

    @Test
    void reflection2() throws Exception {

        //클래스 메타 정보 얻음
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA"); // callA라는 메서드
        dynamicCall(methodCallA,target);

        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB"); // callB라는 메서드
        dynamicCall(methodCallB,target);



    }

    private void dynamicCall(Method method,Object target) throws Exception
    {
        log.info("start");

        // target 안의 method를 invoke 하겠다는 의미!
        // 만약 callB의 Method라면 b.callB()를 호출하겠다는 것이다.
        // invoke 하겠다 -> 누구의? -> target의 메서드를
        Object result = method.invoke(target);
        log.info("result={}",result);
    }


    @Slf4j
    static class Hello
    {
        public String callA()
        {
            log.info("callA");
            return "A";
        }
        public String callB()
        {
            log.info("callB");
            return "B";
        }

    }
}
