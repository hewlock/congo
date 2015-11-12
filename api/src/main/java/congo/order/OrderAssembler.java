package congo.order;

import java.util.Collection;

public interface OrderAssembler
{
	OrderResource assemble(Order order);

	OrderListResource assemble(Collection<Order> orders);
}
