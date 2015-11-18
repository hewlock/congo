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

import congo.product.assemble.ProductAssembler;
import congo.product.assemble.ProductCollectionAssembler;
import congo.product.resource.ProductCollectionResource;
import congo.product.resource.ProductResource;

@Controller
@RequestMapping("/products")
public class ProductController
{
	@Autowired
	ProductService productService;

	@Autowired
	ProductAssembler productAssembler;

	@Autowired
	ProductCollectionAssembler productCollectionAssembler;


	@RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ProductCollectionResource> getProductList()
	{
		Collection<Product> products = productService.getAllProducts();
		ProductCollectionResource resource = productCollectionAssembler.toResource(products);
		return new ResponseEntity<ProductCollectionResource>(resource, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public HttpEntity<ProductResource> getProduct(@PathVariable("id") long id)
	{
		Product product = productService.getProduct(id);
		if (null == product)
		{
			return new ResponseEntity<ProductResource>(HttpStatus.NOT_FOUND);
		}
		ProductResource resource = productAssembler.toResource(product);
		return new ResponseEntity<ProductResource>(resource, HttpStatus.OK);
	}
}
