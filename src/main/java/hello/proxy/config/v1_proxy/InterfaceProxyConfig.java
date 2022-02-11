package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이걸 기준으로 설정 정보를 쭉 읽어온다!
public class InterfaceProxyConfig {

    // 프록시를 스프링 컨테이너에 등록 orderController라는 빈 이름으로 등록
    // 스프링 컨테이너에 등록된 프록시 객체는 target 참조변수에서 실제 impl을 가리키고 있다!
    // 프록시는 컨테이너 올라가고 자바 힙 메모리에 올라간다. 실제 객체는 자바 힙 메모리에만 올라간다!
    // 서로가 서로의 프록시를 호출해준다!
    @Bean  // Bean을 스프링 컨테이너에 등록할때 파라미터에 있는 값들 모두 의존성 주입을 알아서 해준다!
    public OrderControllerV1 orderController(LogTrace logTrace) // 알아서 꼽아줌
    {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl,logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){
        OrderServiceImpl serviceImpl = new OrderServiceImpl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl,logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace)
    {
        OrderRepositoryV1Impl repositoryImpl =  new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl,logTrace);
    }
}
