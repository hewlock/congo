package congo.order;

import java.util.Collection;

public interface OrderService
{
	Order getOrder(long orderId);

	Collection<Order> getAllOrders();

	Order saveOrder(Order order);
}
