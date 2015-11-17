package congo.cart.item.assemble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import congo.cart.CartItem;
import congo.cart.item.ItemController;
import congo.cart.item.resource.ItemGetResource;
import congo.product.ProductController;
import congo.product.assemble.ProductGetAssembler;
import congo.product.resource.ProductGetResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ItemGetAssembler implements ResourceAssembler<CartItem, ItemGetResource>
{
	@Autowired
	RelProvider relProvider;

	@Autowired
	ProductGetAssembler productGetAssembler;


	@Override
	public ItemGetResource toResource(CartItem item)
	{
		return new ItemGetResource(getEmbeds(item), getLinks(item));
	}


	private Collection<Link> getLinks(CartItem item)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ItemController.class).getCartItem(item.getId())).withSelfRel());
		links.add(linkTo(methodOn(ItemController.class).getCartItemList())
			.withRel(relProvider.getItemResourceRelFor(ItemGetResource.class)));
		links.add(linkTo(methodOn(ProductController.class).getProduct(item.getProduct().getId()))
			.withRel(relProvider.getItemResourceRelFor(ProductGetResource.class)));
		return links;
	}


	private Collection<ResourceSupport> getEmbeds(CartItem item)
	{
		ResourceSupport resource = productGetAssembler.toResource(item.getProduct());
		return Collections.singleton(resource);
	}
}
