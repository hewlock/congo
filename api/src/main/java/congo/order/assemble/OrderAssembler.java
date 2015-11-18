package congo.order.assemble;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import congo.order.Order;
import congo.order.OrderController;
import congo.order.resource.OrderCollectionResource;
import congo.order.resource.OrderResource;
import congo.product.Product;
import congo.product.ProductController;
import congo.product.assemble.ProductAssembler;
import congo.product.resource.ProductResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class OrderAssembler implements ResourceAssembler<Order, OrderResource>
{
	@Autowired
	RelProvider relProvider;

	@Autowired
	ProductAssembler productAssembler;


	@Override
	public OrderResource toResource(Order order)
	{
		return new OrderResource(
			getTotal(order.getProducts()),
			order.getCreditCardNumber(),
			order.getAddress(),
			order.getTime(),
			getEmbeds(order),
			getLinks(order));
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


	private Collection<Link> getLinks(Order order)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel());
		links.add(linkTo(methodOn(OrderController.class).getOrderList())
			.withRel(relProvider.getItemResourceRelFor(OrderCollectionResource.class)));
		for (Product product : order.getProducts())
		{
			links.add(linkTo(methodOn(ProductController.class).getProduct(product.getId()))
					.withRel(relProvider.getItemResourceRelFor(ProductResource.class)));
		}
		return links;
	}


	public Collection<ResourceSupport> getEmbeds(Order order)
	{
		Collection<ResourceSupport> resources = new ArrayList<ResourceSupport>();
		for (Product product : order.getProducts())
		{
			resources.add(productAssembler.toResource(product));
		}
		return resources;
	}
}
