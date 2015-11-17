package congo.cart.item.assemble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

import congo.EmbeddedResourceSupport;
import congo.cart.CartItem;
import congo.cart.item.ItemController;
import congo.cart.item.resource.ItemGetResource;
import congo.product.ProductController;
import congo.product.assemble.ProductGetAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ItemGetAssembler implements ResourceAssembler<CartItem, ItemGetResource>
{
	@Autowired
	ProductGetAssembler productGetAssembler;


	@Override
	public ItemGetResource toResource(CartItem item)
	{
		ItemGetResource resource = new ItemGetResource();
		resource.add(getCartItemLinks(item));
		resource.embed(getCartItemEmbeds(item));
		return resource;
	}


	private Collection<Link> getCartItemLinks(CartItem item)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ItemController.class).getCartItem(item.getId())).withSelfRel());
		links.add(linkTo(methodOn(ItemController.class).getCartItemList()).withRel("shopping-cart-items"));
		links.add(linkTo(methodOn(ProductController.class).getProduct(item.getProduct().getId())).withRel("product"));
		return links;
	}


	private Collection<EmbeddedResourceSupport> getCartItemEmbeds(CartItem item)
	{
		EmbeddedResourceSupport resource = productGetAssembler.toResource(item.getProduct());
		return Collections.singleton(resource);
	}
}
