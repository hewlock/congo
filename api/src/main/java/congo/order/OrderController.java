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
import congo.order.assemble.OrderAssembler;
import congo.order.assemble.OrderGetAssembler;
import congo.order.assemble.OrderGetCollectionAssembler;
import congo.order.resource.OrderGetCollectionResource;
import congo.order.resource.OrderGetResource;
import congo.order.resource.OrderPostResource;

@Controller
@RequestMapping("/orders")
public class OrderController
{
	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	@Autowired
	OrderGetAssembler orderGetAssembler;

	@Autowired
	OrderGetCollectionAssembler orderGetCollectionAssembler;

	@Autowired
	OrderAssembler orderAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<OrderGetCollectionResource> getOrderList()
	{
		Collection<Order> orders = orderService.getAllOrders();
		OrderGetCollectionResource resource = orderGetCollectionAssembler.toResource(orders);
		return new ResponseEntity<OrderGetCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<OrderGetResource> getOrder(@PathVariable("id") long id)
	{
		Order order = orderService.getOrder(id);
		if (null == order)
		{
			return new ResponseEntity<OrderGetResource>(HttpStatus.NOT_FOUND);
		}
		OrderGetResource resource = orderGetAssembler.toResource(order);
		return new ResponseEntity<OrderGetResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<OrderGetResource> postOrder(@RequestBody OrderPostResource resource)
	{
		Order order = orderAssembler.fromResource(resource);
		order.addAll(cartService.getAllCartItems());
		if (!order.isValid())
		{
			return new ResponseEntity<OrderGetResource>(HttpStatus.BAD_REQUEST);
		}
		cartService.clear();
		Order persisted = orderService.saveOrder(order);
		OrderGetResource response = orderGetAssembler.toResource(persisted);
		return new ResponseEntity<OrderGetResource>(response, HttpStatus.OK);
	}
}
