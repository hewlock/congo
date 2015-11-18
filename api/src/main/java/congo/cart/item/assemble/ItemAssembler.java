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
import congo.cart.item.resource.ItemCollectionResource;
import congo.cart.item.resource.ItemResource;
import congo.product.ProductController;
import congo.product.assemble.ProductAssembler;
import congo.product.resource.ProductResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ItemAssembler implements ResourceAssembler<CartItem, ItemResource>
{
	@Autowired
	RelProvider relProvider;

	@Autowired
	ProductAssembler productAssembler;


	@Override
	public ItemResource toResource(CartItem item)
	{
		return new ItemResource(getEmbeds(item), getLinks(item));
	}


	private Collection<Link> getLinks(CartItem item)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ItemController.class).getCartItem(item.getId())).withSelfRel());
		links.add(linkTo(methodOn(ItemController.class).getCartItemList())
			.withRel(relProvider.getItemResourceRelFor(ItemCollectionResource.class)));
		links.add(linkTo(methodOn(ProductController.class).getProduct(item.getProduct().getId()))
			.withRel(relProvider.getItemResourceRelFor(ProductResource.class)));
		return links;
	}


	private Collection<ResourceSupport> getEmbeds(CartItem item)
	{
		ResourceSupport resource = productAssembler.toResource(item.getProduct());
		return Collections.singleton(resource);
	}
}
