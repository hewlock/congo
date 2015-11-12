package shop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	ProductService productService;


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ProductResource> getProduct(@PathVariable("id") long id)
	{
		Product product = productService.findProduct(id);

		ResponseEntity<ProductResource> response = null;
		if (null != product)
		{
			ProductResource productResource = new ProductResource(product);
			productResource.add(linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel());
			response = new ResponseEntity<>(productResource, HttpStatus.OK);
		}
		else
		{
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
}
