package congo.cart.item.assemble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import congo.DomainFactory;
import congo.cart.CartItem;
import congo.cart.item.resource.ItemForm;
import congo.product.Product;
import congo.product.ProductController;
import congo.product.ProductService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class CartItemFactory implements DomainFactory<ItemForm, CartItem>
{
	@Autowired
	ProductService productService;


	@Override
	public CartItem fromResource(ItemForm resource)
	{
		String prefix = linkTo(methodOn(ProductController.class).getProductList()).toString();
		long productId = Long.parseLong(resource.getProduct().replace(prefix, ""));
		Product product = productService.getProduct(productId);
		return new CartItem(product);
	}
}
