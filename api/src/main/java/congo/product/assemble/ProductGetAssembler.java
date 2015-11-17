package congo.product.assemble;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

import congo.product.Product;
import congo.product.ProductController;
import congo.product.resource.ProductGetResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ProductGetAssembler implements ResourceAssembler<Product, ProductGetResource>
{
	@Override
	public ProductGetResource toResource(Product product)
	{
		ProductGetResource resource = new ProductGetResource(product.getName(), product.getDescription(), product.getPrice());
		resource.add(getProductLinks(product));
		return resource;
	}


	private Collection<Link> getProductLinks(Product product)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
		links.add(linkTo(methodOn(ProductController.class).getProductList()).withRel("products"));
		return links;
	}
}
