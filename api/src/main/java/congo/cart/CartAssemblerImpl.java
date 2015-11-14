package congo.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.EmbeddedResourceSupport;
import congo.product.Product;
import congo.product.ProductAssembler;
import congo.product.ProductController;
import congo.product.ProductService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
class CartAssemblerImpl implements CartAssembler
{
	@Autowired
	ProductAssembler productAssembler;

	@Autowired
	ProductService productService;


	@Override
	public CartItemResource assemble(CartItem item)
	{
		CartItemResource resource = new CartItemResource();
		resource.add(getCartItemLinks(item));
		resource.embed(getCartItemEmbeds(item));
		return resource;
	}


	public Collection<Link> getCartItemLinks(CartItem item)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(CartController.class).getCartItem(item.getId())).withSelfRel());
		links.add(linkTo(methodOn(CartController.class).getCartItemList()).withRel("cart-items"));
		links.add(linkTo(methodOn(ProductController.class).getProduct(item.getProduct().getId())).withRel("product"));
		return links;
	}


	public Collection<EmbeddedResourceSupport> getCartItemEmbeds(CartItem item)
	{
		EmbeddedResourceSupport resource = productAssembler.assemble(item.getProduct());
		return Collections.singleton(resource);
	}


	@Override
	public CartItemListResource assemble(Collection<CartItem> items)
	{
		CartItemListResource resource = new CartItemListResource(getTotal(items));
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


	public Collection<Link> getCartItemListLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(CartController.class).getCartItemList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(CartController.class), "id")), "cart-item"));
		return links;
	}


	public Collection<EmbeddedResourceSupport> getCartItemListEmbeds(Collection<CartItem> items)
	{
		Collection<EmbeddedResourceSupport> resources = new ArrayList<EmbeddedResourceSupport>();
		for (CartItem item : items)
		{
			resources.add(assemble(item));
		}
		return resources;
	}


	@Override
	public CartItem assemble(CartItemForm form)
	{
		String prefix = linkTo(methodOn(ProductController.class).getProductList()).toString();
		long productId = Long.parseLong(form.getProduct().replace(prefix, ""));
		Product product = productService.getProduct(productId);
		return new CartItem(product);
	}
}
