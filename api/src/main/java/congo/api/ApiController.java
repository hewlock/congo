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

import congo.api.resource.ApiGetResource;
import congo.cart.item.ItemController;
import congo.cart.item.resource.ItemGetCollectionResource;
import congo.order.OrderController;
import congo.order.resource.OrderGetCollectionResource;
import congo.product.ProductController;
import congo.product.resource.ProductGetCollectionResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/api")
public class ApiController
{
	@Autowired
	RelProvider relProvider;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ApiGetResource> getApi()
	{
		ApiGetResource resource = new ApiGetResource();
		resource.add(linkTo(methodOn(ApiController.class).getApi()).withSelfRel());
		resource.add(linkTo(methodOn(ItemController.class).getCartItemList())
			.withRel(relProvider.getItemResourceRelFor(ItemGetCollectionResource.class)));
		resource.add(linkTo(methodOn(OrderController.class).getOrderList())
			.withRel(relProvider.getItemResourceRelFor(OrderGetCollectionResource.class)));
		resource.add(linkTo(methodOn(ProductController.class).getProductList())
			.withRel(relProvider.getItemResourceRelFor(ProductGetCollectionResource.class)));
		return new ResponseEntity<ApiGetResource>(resource, HttpStatus.OK);
	}
}
