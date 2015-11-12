package congo.cart;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import congo.product.Product;
import congo.product.ProductController;
import congo.product.ProductService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	CartAssembler cartAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<CartItemListResource> getCartItemList()
	{
		Collection<CartItem> items = cartService.getAllCartItems();
		CartItemListResource cartItemListResource = cartAssembler.assemble(items);
		return new ResponseEntity<CartItemListResource>(cartItemListResource, HttpStatus.OK);
	}


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


	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<CartItemResource> createCartItem(@RequestBody CartItemForm form)
	{
		String prefix = linkTo(methodOn(ProductController.class).getProductList()).toString();
		String productId = form.getProduct().replace(prefix, "");
		Product product = productService.getProduct(Long.parseLong(productId));

		ResponseEntity<CartItemResource> response = null;
		if (null != product)
		{
			CartItem item = cartService.saveItem(new CartItem(product));
			CartItemResource resource = cartAssembler.assemble(item);
			response = new ResponseEntity<CartItemResource>(resource, HttpStatus.OK);
		}
		else
		{
			response = new ResponseEntity<CartItemResource>(HttpStatus.BAD_REQUEST);
		}
		return response;
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpEntity<Void> deleteCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.deleteItem(id);
		HttpStatus httpStatus = (null != item) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Void>(httpStatus);
	}
}
