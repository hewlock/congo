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

import congo.product.assemble.ProductGetAssembler;
import congo.product.assemble.ProductGetCollectionAssembler;
import congo.product.resource.ProductGetCollectionResource;
import congo.product.resource.ProductGetResource;

@Controller
@RequestMapping("/product")
public class ProductController
{
	@Autowired
	ProductService productService;

	@Autowired
	ProductGetAssembler productGetAssembler;

	@Autowired
	ProductGetCollectionAssembler productGetCollectionAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ProductGetCollectionResource> getProductList()
	{
		Collection<Product> products = productService.getAllProducts();
		ProductGetCollectionResource resource = productGetCollectionAssembler.assemble(products);
		return new ResponseEntity<ProductGetCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<ProductGetResource> getProduct(@PathVariable("id") long id)
	{
		Product product = productService.getProduct(id);
		if (null == product)
		{
			return new ResponseEntity<ProductGetResource>(HttpStatus.NOT_FOUND);
		}
		ProductGetResource resource = productGetAssembler.assemble(product);
		return new ResponseEntity<ProductGetResource>(resource, HttpStatus.OK);
	}
}
