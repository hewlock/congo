package congo.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.EmbeddedResourceSupport;
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
		OrderResource resource = new OrderResource(
			getTotal(order.getProducts()),
			order.getCreditCardNumber(),
			order.getAddress(),
			order.getTime());
		resource.add(getOrderLinks(order));
		resource.embed(getOrderEmbeds(order));
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


	private Collection<Link> getOrderLinks(Order order)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel());
		links.add(linkTo(methodOn(OrderController.class).getOrderList()).withRel("orders"));
		return links;
	}


	public Collection<EmbeddedResourceSupport> getOrderEmbeds(Order order)
	{
		Collection<EmbeddedResourceSupport> resources = new ArrayList<EmbeddedResourceSupport>();
		for (Product product : order.getProducts())
		{
			resources.add(productAssembler.assemble(product));
		}
		return resources;
	}


	@Override
	public OrderListResource assemble(Collection<Order> orders)
	{
		OrderListResource resource = new OrderListResource();
		resource.add(getOrderListLinks());
		resource.embed(getOrderListEmbeds(orders));
		return resource;
	}


	public Collection<Link> getOrderListLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(OrderController.class).getOrderList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(OrderController.class), "id")), "order"));
		return links;
	}


	public Collection<EmbeddedResourceSupport> getOrderListEmbeds(Collection<Order> orders)
	{
		Collection<EmbeddedResourceSupport> resources = new ArrayList<EmbeddedResourceSupport>();
		for (Order order : orders)
		{
			resources.add(assemble(order));
		}
		return resources;
	}


	@Override
	public Order assemble(OrderForm form)
	{
		return new Order(
				form.getCreditCardNumber(),
				form.getAddress());
	}
}
