package congo.order;


import java.util.ArrayList;
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

import congo.cart.CartItem;
import congo.cart.CartService;
import congo.product.Product;

@Controller
@RequestMapping("/order")
public class OrderController
{
	@Autowired
	CartService cartService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderAssembler orderAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<OrderListResource> getOrderList()
	{
		Collection<Order> orders = orderService.getAllOrders();
		OrderListResource listResource = orderAssembler.assemble(orders);
		return new ResponseEntity<OrderListResource>(listResource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<OrderResource> getOrder(@PathVariable("id") long id)
	{
		Order order = orderService.getOrder(id);

		ResponseEntity<OrderResource> response = null;
		if (null != order)
		{
			OrderResource resource = orderAssembler.assemble(order);
			response = new ResponseEntity<OrderResource>(resource, HttpStatus.OK);
		}
		else
		{
			response = new ResponseEntity<OrderResource>(HttpStatus.NOT_FOUND);
		}
		return response;
	}


	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<OrderResource> createOrder(@RequestBody OrderForm form)
	{
		Order order = getOrder(form.getCreditCardNumber());

		ResponseEntity<OrderResource> response = null;
		if (order.isEmpty())
		{
			response = new ResponseEntity<OrderResource>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			OrderResource resource = orderAssembler.assemble(orderService.saveOrder(order));
			response = new ResponseEntity<OrderResource>(resource, HttpStatus.OK);
		}
		return response;
	}


	private Order getOrder(String creditCardNumber)
	{
		Collection<CartItem> cartItems = cartService.removeAllCartItems();
		Collection<Product> products = new ArrayList<Product>(cartItems.size());
		for (CartItem cartItem : cartItems)
		{
			products.add(cartItem.getProduct());
		}
		return new Order(products, creditCardNumber);
	}

}
