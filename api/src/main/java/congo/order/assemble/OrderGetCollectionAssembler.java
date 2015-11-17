package congo.order.assemble;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.order.Order;
import congo.order.OrderController;
import congo.order.resource.OrderGetCollectionResource;
import congo.order.resource.OrderGetResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class OrderGetCollectionAssembler implements ResourceAssembler<Collection<Order>, OrderGetCollectionResource>
{
	@Autowired
	RelProvider relProvider;

	@Autowired
	OrderGetAssembler orderGetAssembler;


	@Override
	public OrderGetCollectionResource toResource(Collection<Order> orders)
	{
		return new OrderGetCollectionResource(getEmbeds(orders), getLinks());
	}


	public Collection<Link> getLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(OrderController.class).getOrderList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(OrderController.class), "id")),
			relProvider.getItemResourceRelFor(OrderGetResource.class)));
		return links;
	}


	public Collection<ResourceSupport> getEmbeds(Collection<Order> orders)
	{
		Collection<ResourceSupport> resources = new ArrayList<ResourceSupport>();
		for (Order order : orders)
		{
			resources.add(orderGetAssembler.toResource(order));
		}
		return resources;
	}
}
