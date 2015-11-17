package congo.order.assemble;

import org.springframework.stereotype.Service;

import congo.DomainAssembler;
import congo.order.Order;
import congo.order.resource.OrderPostResource;

@Service
public class OrderAssembler implements DomainAssembler<OrderPostResource, Order>
{
	@Override
	public Order fromResource(OrderPostResource resource)
	{
		return new Order(resource.getCreditCardNumber(), resource.getAddress());
	}
}
