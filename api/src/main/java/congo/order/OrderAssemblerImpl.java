package congo.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.cart.CartItem;
import congo.cart.CartService;
import congo.product.Product;
import congo.product.ProductAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
class OrderAssemblerImpl implements OrderAssembler
{
	@Autowired
	ProductAssembler productAssembler;

	@Autowired
	CartService cartService;


	@Override
	public OrderResource assemble(Order order)
	{
		OrderResource resource = getOrderResource(order);
		for (Product product : order.getProducts())
		{
			resource.embed(productAssembler.assemble(product));
		}
		return resource;
	}


	private OrderResource getOrderResource(Order order)
	{
		OrderResource resource = new OrderResource(
			getTotal(order.getProducts()),
			order.getCreditCardNumber(),
			order.getTime());

		resource.add(linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel());
		resource.add(linkTo(methodOn(OrderController.class).getOrderList()).withRel("orders"));

		return resource;
	}


	private BigDecimal getTotal(Collection<Product> products)
	{
		BigDecimal total = new BigDecimal("0.00");
		for (Product product : products)
		{
			total = total.add(product.getPrice());
		}
		return total;
	}


	@Override
	public OrderListResource assemble(Collection<Order> orders)
	{
		OrderListResource listResource = new OrderListResource();
		for (Order order : orders)
		{
			listResource.embed(getOrderResource(order));
			for (Product product : order.getProducts())
			{
				listResource.embed(productAssembler.assemble(product));
			}
		}

		listResource.add(linkTo(methodOn(OrderController.class).getOrderList()).withSelfRel());
		listResource.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(OrderController.class), "id")), "order"));

		return listResource;
	}


	@Override
	public Order assemble(OrderForm form)
	{
		String creditCardNumber = form.getCreditCardNumber();
		Collection<CartItem> cartItems = cartService.removeAllCartItems();
		Collection<Product> products = new ArrayList<Product>(cartItems.size());
		for (CartItem cartItem : cartItems)
		{
			products.add(cartItem.getProduct());
		}
		return new Order(products, creditCardNumber);
	}
}
