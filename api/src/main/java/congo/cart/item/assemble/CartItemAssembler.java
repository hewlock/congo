package congo.cart.item.assemble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import congo.Assembler;
import congo.cart.CartItem;
import congo.cart.item.resource.ItemPostResource;
import congo.product.Product;
import congo.product.ProductController;
import congo.product.ProductService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class CartItemAssembler implements Assembler<ItemPostResource, CartItem>
{
	@Autowired
	ProductService productService;


	@Override
	public CartItem assemble(ItemPostResource resource)
	{
		String prefix = linkTo(methodOn(ProductController.class).getProductList()).toString();
		long productId = Long.parseLong(resource.getProduct().replace(prefix, ""));
		Product product = productService.getProduct(productId);
		return new CartItem(product);
	}
}
