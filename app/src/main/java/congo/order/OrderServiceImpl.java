package congo.order;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
class OrderServiceImpl implements OrderService
{
	private long nextId = 1;
	private final Map<Long, Order> orders = new HashMap<Long, Order>();


	@Override
	public Order getOrder(long orderId)
	{
		return orders.get(orderId);
	}


	@Override
	public Collection<Order> getAllOrders()
	{
		return orders.values();
	}


	@Override
	public Order saveOrder(Order order)
	{
		Order persisted = new Order(nextId++, order.getProducts(), order.getCreditCardNumber(), order.getTime());
		orders.put(persisted.getId(), persisted);
		return persisted;
	}
}
