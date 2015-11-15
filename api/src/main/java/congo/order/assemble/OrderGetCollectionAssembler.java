package congo.order.assemble;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.Assembler;
import congo.EmbeddedResourceSupport;
import congo.order.Order;
import congo.order.OrderController;
import congo.order.resource.OrderGetCollectionResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class OrderGetCollectionAssembler implements Assembler<Collection<Order>, OrderGetCollectionResource>
{
	@Autowired
	OrderGetAssembler orderGetAssembler;


	@Override
	public OrderGetCollectionResource assemble(Collection<Order> orders)
	{
		OrderGetCollectionResource resource = new OrderGetCollectionResource();
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
			resources.add(orderGetAssembler.assemble(order));
		}
		return resources;
	}
}
