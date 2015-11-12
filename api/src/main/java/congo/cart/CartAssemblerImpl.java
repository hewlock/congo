package congo.cart;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import congo.product.ProductAssembler;
import congo.product.ProductController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
class CartAssemblerImpl implements CartAssembler
{
	@Autowired
	ProductAssembler productAssembler;


	@Override
	public CartItemResource assemble(CartItem item)
	{
		CartItemResource resource = new CartItemResource();

		resource.add(linkTo(methodOn(CartController.class).getCartItem(item.getId())).withSelfRel());
		resource.add(linkTo(methodOn(CartController.class).getCartItemList()).withRel("cart-items"));
		resource.add(linkTo(methodOn(ProductController.class).getProduct(item.getProduct().getId())).withRel("product"));

		return resource;
	}


	@Override
	public CartItemListResource assemble(Collection<CartItem> items)
	{
		CartItemListResource listResource = new CartItemListResource(getTotal(items));
		for (CartItem item : items)
		{
			listResource.embed(assemble(item));
			listResource.embed(productAssembler.assemble(item.getProduct()));
		}
		return listResource;
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
}
