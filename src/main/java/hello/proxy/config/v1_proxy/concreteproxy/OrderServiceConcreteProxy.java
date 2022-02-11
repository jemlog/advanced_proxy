package hello.proxy.config.v1_proxy.concreteproxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

/**
 * 자식클래스를 생성할때 부모 클래스의 생성자를 호출해줘야 한다.
 * 기본 생성자만 필요하다면 super()이 자동으로 호출된다. 그치만 기본 생성자 말고 인자가 필요하다면
 * super(해당 인자)로 따로 호출 필요하다. 만약 부모의 기능을 원하지 않는다면 super(null) 사용하면 된다!
 */
public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null); // 부모의 기능을 사용하지 않고 강제 호출만 해야해서 null을 넣음!
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status =  logTrace.begin("OrderService.request()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);
        }
        catch (Exception e)
        {
            logTrace.exception(status,e);
            throw e;
        }
    }
}
