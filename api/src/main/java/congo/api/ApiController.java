package congo.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import congo.api.resource.ApiGetResource;
import congo.cart.CartController;
import congo.order.OrderController;
import congo.product.ProductController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/api")
public class ApiController
{
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ApiGetResource> getApi()
	{
		ApiGetResource resource = new ApiGetResource();
		resource.add(linkTo(methodOn(ApiController.class).getApi()).withSelfRel());
		resource.add(linkTo(methodOn(CartController.class).getCartItemList()).withRel("cart-items"));
		resource.add(linkTo(methodOn(OrderController.class).getOrderList()).withRel("orders"));
		resource.add(linkTo(methodOn(ProductController.class).getProductList()).withRel("products"));
		return new ResponseEntity<ApiGetResource>(resource, HttpStatus.OK);
	}
}
