package hello.proxy.config.v1_proxy.concreteproxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // 지금은 프록시가 자신의 부모타입을 상속받았다.
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

    private final OrderRepositoryV2 target;
    private final LogTrace logTrace;

    public OrderRepositoryConcreteProxy(OrderRepositoryV2 target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status =  logTrace.begin("OrderRepository.request()");
            //target 호출
            target.save(itemId);
            logTrace.end(status);
        }
        catch (Exception e)
        {
            logTrace.exception(status,e);
            throw e;
        }
    }
}
