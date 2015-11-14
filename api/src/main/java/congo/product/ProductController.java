package congo.product;

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

@Controller
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	ProductService productService;

	@Autowired
	ProductAssembler productAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ProductListResource> getProductList()
	{
		Collection<Product> products = productService.getAllProducts();
		ProductListResource resource = productAssembler.assemble(products);
		return new ResponseEntity<ProductListResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ProductResource> getProduct(@PathVariable("id") long id)
	{
		Product product = productService.getProduct(id);
		if(null == product)
		{
			return new ResponseEntity<ProductResource>(HttpStatus.NOT_FOUND);
		}
		ProductResource resource = productAssembler.assemble(product);
		return new ResponseEntity<ProductResource>(resource, HttpStatus.OK);
	}
}
