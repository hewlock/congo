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

@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartService;

	@Autowired
	CartAssembler cartAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<CartItemListResource> getCartItemList()
	{
		Collection<CartItem> items = cartService.getAllCartItems();
		CartItemListResource resource = cartAssembler.assemble(items);
		return new ResponseEntity<CartItemListResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<CartItemResource> getCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.getCartItem(id);
		if (null == item)
		{
			return new ResponseEntity<CartItemResource>(HttpStatus.NOT_FOUND);
		}
		CartItemResource resource = cartAssembler.assemble(item);
		return new ResponseEntity<CartItemResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<CartItemResource> postCartItem(@RequestBody CartItemForm form)
	{
		CartItem item = cartAssembler.assemble(form);
		if (!item.isValid())
		{
			return new ResponseEntity<CartItemResource>(HttpStatus.BAD_REQUEST);
		}
		CartItem persisted = cartService.saveItem(item);
		CartItemResource resource = cartAssembler.assemble(persisted);
		return new ResponseEntity<CartItemResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpEntity<Void> deleteCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.deleteItem(id);
		if (null == item)
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
