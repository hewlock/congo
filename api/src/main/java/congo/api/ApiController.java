package congo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RelProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import congo.api.resource.ApiResource;
import congo.cart.item.ItemController;
import congo.cart.item.resource.ItemCollectionResource;
import congo.order.OrderController;
import congo.order.resource.OrderCollectionResource;
import congo.product.ProductController;
import congo.product.resource.ProductCollectionResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/api")
public class ApiController
{
	@Autowired
	RelProvider relProvider;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ApiResource> getApi()
	{
		ApiResource resource = new ApiResource();
		resource.add(linkTo(methodOn(ApiController.class).getApi()).withSelfRel());
		resource.add(linkTo(methodOn(ItemController.class).getCartItemList())
			.withRel(relProvider.getItemResourceRelFor(ItemCollectionResource.class)));
		resource.add(linkTo(methodOn(OrderController.class).getOrderList())
			.withRel(relProvider.getItemResourceRelFor(OrderCollectionResource.class)));
		resource.add(linkTo(methodOn(ProductController.class).getProductList())
			.withRel(relProvider.getItemResourceRelFor(ProductCollectionResource.class)));
		return new ResponseEntity<ApiResource>(resource, HttpStatus.OK);
	}
}
