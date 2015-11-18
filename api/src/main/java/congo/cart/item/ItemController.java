package congo.cart.item;

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

import congo.cart.CartItem;
import congo.cart.CartService;
import congo.cart.item.assemble.CartItemFactory;
import congo.cart.item.assemble.ItemAssembler;
import congo.cart.item.assemble.ItemCollectionAssembler;
import congo.cart.item.resource.ItemCollectionResource;
import congo.cart.item.resource.ItemResource;
import congo.cart.item.resource.ItemForm;

@Controller
@RequestMapping("/cart/items")
public class ItemController
{
	@Autowired
	CartService cartService;

	@Autowired
	ItemAssembler itemAssembler;

	@Autowired
	ItemCollectionAssembler itemCollectionAssembler;

	@Autowired
	CartItemFactory cartItemFactory;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ItemCollectionResource> getCartItemList()
	{
		Collection<CartItem> items = cartService.getAllCartItems();
		ItemCollectionResource resource = itemCollectionAssembler.toResource(items);
		return new ResponseEntity<ItemCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ItemResource> getCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.getCartItem(id);
		if (null == item)
		{
			return new ResponseEntity<ItemResource>(HttpStatus.NOT_FOUND);
		}
		ItemResource resource = itemAssembler.toResource(item);
		return new ResponseEntity<ItemResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ItemResource> postCartItem(@RequestBody ItemForm resource)
	{
		CartItem item = cartItemFactory.fromResource(resource);
		if (!item.isValid())
		{
			return new ResponseEntity<ItemResource>(HttpStatus.BAD_REQUEST);
		}
		CartItem persisted = cartService.saveItem(item);
		ItemResource response = itemAssembler.toResource(persisted);
		return new ResponseEntity<ItemResource>(response, HttpStatus.OK);
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
