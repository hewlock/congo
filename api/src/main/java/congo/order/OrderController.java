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
import congo.order.assemble.OrderFactory;
import congo.order.assemble.OrderAssembler;
import congo.order.assemble.OrderCollectionAssembler;
import congo.order.resource.OrderCollectionResource;
import congo.order.resource.OrderResource;
import congo.order.resource.OrderForm;

@Controller
@RequestMapping("/orders")
public class OrderController
{
	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	@Autowired
	OrderAssembler orderAssembler;

	@Autowired
	OrderCollectionAssembler orderCollectionAssembler;

	@Autowired
	OrderFactory orderFactory;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<OrderCollectionResource> getOrderList()
	{
		Collection<Order> orders = orderService.getAllOrders();
		OrderCollectionResource resource = orderCollectionAssembler.toResource(orders);
		return new ResponseEntity<OrderCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<OrderResource> getOrder(@PathVariable("id") long id)
	{
		Order order = orderService.getOrder(id);
		if (null == order)
		{
			return new ResponseEntity<OrderResource>(HttpStatus.NOT_FOUND);
		}
		OrderResource resource = orderAssembler.toResource(order);
		return new ResponseEntity<OrderResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<OrderResource> postOrder(@RequestBody OrderForm resource)
	{
		Order order = orderFactory.fromResource(resource);
		order.addAll(cartService.getAllCartItems());
		if (!order.isValid())
		{
			return new ResponseEntity<OrderResource>(HttpStatus.BAD_REQUEST);
		}
		cartService.clear();
		Order persisted = orderService.saveOrder(order);
		OrderResource response = orderAssembler.toResource(persisted);
		return new ResponseEntity<OrderResource>(response, HttpStatus.OK);
	}
}
