package hello.proxy.myTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


// JDK 동적 프록시에 적용할 로직을 명시할 수 있다.
@Slf4j
public class MyInvocationHandler implements InvocationHandler {

    // 반드시 구체 대상 클래스 정보를 받아와야 한다.
    private final Object target;
    private final String[] patterns;

    public MyInvocationHandler(Object target, String[] patterns) {
        this.target = target;
        this.patterns = patterns;
    }

    @Override             // 실행 로직에서 어떤 메서드 호출했는지 method에 들어옴, 어떤 변수를 넣었는지 args에 들어온다.
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        if(!PatternMatchUtils.simpleMatch(patterns,methodName))
        {
            return method.invoke(target,args);
        }
        log.info("invoke 호출!");
        // method.invoke(target,args) -> method를 invoke하는데 target이라는 구체클래스의 method를 호출하겠다는 말이다. args로 인자 넘기기 가능
        Object result = method.invoke(target, args);
        return result;  // 구체 클래스에서 넘어오는 값을 그대로 프록시에서 return 해줘야 한다.
    }
}
