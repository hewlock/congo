package congo.cart;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import congo.product.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartService;

	@Autowired
	CartAssembler cartAssembler;

	// Deleteme
	@Autowired
	ProductService productService;


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<CartItemResource> getCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.getCartItem(id);

		ResponseEntity<CartItemResource> response = null;
		if (null != item)
		{
			CartItemResource resource = cartAssembler.assemble(item);
			response = new ResponseEntity<CartItemResource>(resource, HttpStatus.OK);
		}
		else
		{
			response = new ResponseEntity<CartItemResource>(HttpStatus.NOT_FOUND);
		}
		return response;
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<CartItemListResource> getCartItemList()
	{
		// deleteme
		cartService.saveItem(new CartItem(productService.getProduct(1)));
		cartService.saveItem(new CartItem(productService.getProduct(2)));

		Collection<CartItem> items = cartService.getAllCartItems();
		CartItemListResource cartItemListResource = cartAssembler.assemble(items);
		return new ResponseEntity<CartItemListResource>(cartItemListResource, HttpStatus.OK);
	}
}
