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

import congo.cart.assemble.CartItemAssembler;
import congo.cart.assemble.ItemGetAssembler;
import congo.cart.assemble.ItemGetCollectionAssembler;
import congo.cart.resource.ItemGetCollectionResource;
import congo.cart.resource.ItemGetResource;
import congo.cart.resource.ItemPostResource;

@Controller
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	CartService cartService;

	@Autowired
	ItemGetAssembler itemGetAssembler;

	@Autowired
	ItemGetCollectionAssembler itemGetCollectionAssembler;

	@Autowired
	CartItemAssembler itemPostAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ItemGetCollectionResource> getCartItemList()
	{
		Collection<CartItem> items = cartService.getAllCartItems();
		ItemGetCollectionResource resource = itemGetCollectionAssembler.assemble(items);
		return new ResponseEntity<ItemGetCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ItemGetResource> getCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.getCartItem(id);
		if (null == item)
		{
			return new ResponseEntity<ItemGetResource>(HttpStatus.NOT_FOUND);
		}
		ItemGetResource resource = itemGetAssembler.assemble(item);
		return new ResponseEntity<ItemGetResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<ItemGetResource> postCartItem(@RequestBody ItemPostResource resource)
	{
		CartItem item = itemPostAssembler.assemble(resource);
		if (!item.isValid())
		{
			return new ResponseEntity<ItemGetResource>(HttpStatus.BAD_REQUEST);
		}
		CartItem persisted = cartService.saveItem(item);
		ItemGetResource response = itemGetAssembler.assemble(persisted);
		return new ResponseEntity<ItemGetResource>(response, HttpStatus.OK);
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
