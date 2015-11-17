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
import congo.cart.item.assemble.CartItemAssembler;
import congo.cart.item.assemble.ItemGetAssembler;
import congo.cart.item.assemble.ItemGetCollectionAssembler;
import congo.cart.item.resource.ItemGetCollectionResource;
import congo.cart.item.resource.ItemGetResource;
import congo.cart.item.resource.ItemPostResource;

@Controller
@RequestMapping("/cart/items")
public class ItemController
{
	@Autowired
	CartService cartService;

	@Autowired
	ItemGetAssembler itemGetAssembler;

	@Autowired
	ItemGetCollectionAssembler itemGetCollectionAssembler;

	@Autowired
	CartItemAssembler itemPostAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ItemGetCollectionResource> getCartItemList()
	{
		Collection<CartItem> items = cartService.getAllCartItems();
		ItemGetCollectionResource resource = itemGetCollectionAssembler.toResource(items);
		return new ResponseEntity<ItemGetCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ItemGetResource> getCartItem(@PathVariable("id") long id)
	{
		CartItem item = cartService.getCartItem(id);
		if (null == item)
		{
			return new ResponseEntity<ItemGetResource>(HttpStatus.NOT_FOUND);
		}
		ItemGetResource resource = itemGetAssembler.toResource(item);
		return new ResponseEntity<ItemGetResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/", method = RequestMethod.POST, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ItemGetResource> postCartItem(@RequestBody ItemPostResource resource)
	{
		CartItem item = itemPostAssembler.fromResource(resource);
		if (!item.isValid())
		{
			return new ResponseEntity<ItemGetResource>(HttpStatus.BAD_REQUEST);
		}
		CartItem persisted = cartService.saveItem(item);
		ItemGetResource response = itemGetAssembler.toResource(persisted);
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
