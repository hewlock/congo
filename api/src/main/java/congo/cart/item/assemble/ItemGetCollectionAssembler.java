package congo.cart.item.assemble;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.cart.CartItem;
import congo.cart.item.ItemController;
import congo.cart.item.resource.ItemGetCollectionResource;
import congo.cart.item.resource.ItemGetResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ItemGetCollectionAssembler implements ResourceAssembler<Collection<CartItem>, ItemGetCollectionResource>
{
	@Autowired
	RelProvider relProvider;

	@Autowired
	ItemGetAssembler itemGetAssembler;


	@Override
	public ItemGetCollectionResource toResource(Collection<CartItem> items)
	{
		return new ItemGetCollectionResource(getTotal(items), getEmbeds(items), getLinks());
	}


	private BigDecimal getTotal(Collection<CartItem> items)
	{
		BigDecimal total = new BigDecimal("0.00");
		for (CartItem item : items)
		{
			total = total.add(item.getProduct().getPrice());
		}
		return total;
	}


	private Collection<Link> getLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ItemController.class).getCartItemList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(ItemController.class), "id")),
			relProvider.getItemResourceRelFor(ItemGetResource.class)));
		return links;
	}


	private Collection<ResourceSupport> getEmbeds(Collection<CartItem> items)
	{
		Collection<ResourceSupport> resources = new ArrayList<ResourceSupport>();
		for (CartItem item : items)
		{
			resources.add(itemGetAssembler.toResource(item));
		}
		return resources;
	}
}
