package congo.cart.item.assemble;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.Assembler;
import congo.EmbeddedResourceSupport;
import congo.cart.item.ItemController;
import congo.cart.CartItem;
import congo.cart.item.resource.ItemGetCollectionResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ItemGetCollectionAssembler implements Assembler<Collection<CartItem>, ItemGetCollectionResource>
{
	@Autowired
	ItemGetAssembler itemGetAssembler;


	@Override
	public ItemGetCollectionResource assemble(Collection<CartItem> items)
	{
		ItemGetCollectionResource resource = new ItemGetCollectionResource(getTotal(items));
		resource.add(getCartItemListLinks());
		resource.embed(getCartItemListEmbeds(items));
		return resource;
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


	private Collection<Link> getCartItemListLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ItemController.class).getCartItemList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(ItemController.class), "id")), "shopping-cart-item"));
		return links;
	}


	private Collection<EmbeddedResourceSupport> getCartItemListEmbeds(Collection<CartItem> items)
	{
		Collection<EmbeddedResourceSupport> resources = new ArrayList<EmbeddedResourceSupport>();
		for (CartItem item : items)
		{
			resources.add(itemGetAssembler.assemble(item));
		}
		return resources;
	}
}
