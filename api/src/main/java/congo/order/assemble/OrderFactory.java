package congo.order.assemble;

import org.springframework.stereotype.Service;

import congo.DomainFactory;
import congo.order.Order;
import congo.order.resource.OrderForm;

@Service
public class OrderFactory implements DomainFactory<OrderForm, Order>
{
	@Override
	public Order fromResource(OrderForm resource)
	{
		return new Order(resource.getCreditCardNumber(), resource.getAddress());
	}
}
