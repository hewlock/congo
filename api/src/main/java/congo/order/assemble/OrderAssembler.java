package congo.order.assemble;

import org.springframework.stereotype.Service;

import congo.Assembler;
import congo.order.Order;
import congo.order.resource.OrderPostResource;

@Service
public class OrderAssembler implements Assembler<OrderPostResource, Order>
{
	@Override
	public Order assemble(OrderPostResource resource)
	{
		return new Order(
			resource.getCreditCardNumber(),
			resource.getAddress());
	}
}
