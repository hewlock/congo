package congo.order;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import congo.cart.CartService;

@Controller
@RequestMapping("/order")
public class OrderController
{
	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	@Autowired
	OrderAssembler orderAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<OrderListResource> getOrderList()
	{
		Collection<Order> orders = orderService.getAllOrders();
		OrderListResource resource = orderAssembler.assemble(orders);
		return new ResponseEntity<OrderListResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<OrderResource> getOrder(@PathVariable("id") long id)
	{
		Order order = orderService.getOrder(id);
		if (null == order)
		{
			return new ResponseEntity<OrderResource>(HttpStatus.NOT_FOUND);
		}
		OrderResource resource = orderAssembler.assemble(order);
		return new ResponseEntity<OrderResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<OrderResource> postOrder(@RequestBody OrderForm form)
	{
		Order order = orderAssembler.assemble(form);
		order.addAll(cartService.getAllCartItems());
		if (!order.isValid())
		{
			return new ResponseEntity<OrderResource>(HttpStatus.BAD_REQUEST);
		}
		cartService.clear();
		Order persisted = orderService.saveOrder(order);
		OrderResource resource = orderAssembler.assemble(persisted);
		return new ResponseEntity<OrderResource>(resource, HttpStatus.OK);
	}
}
