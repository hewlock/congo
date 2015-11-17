package congo.product.assemble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import congo.product.Product;
import congo.product.ProductController;
import congo.product.resource.ProductGetCollectionResource;
import congo.product.resource.ProductGetResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ProductGetAssembler implements ResourceAssembler<Product, ProductGetResource>
{
	@Autowired
	RelProvider relProvider;


	@Override
	public ProductGetResource toResource(Product product)
	{
		return new ProductGetResource(
			product.getName(),
			product.getDescription(),
			product.getPrice(),
			Collections.<ResourceSupport>emptySet(),
			getLinks(product));
	}


	private Collection<Link> getLinks(Product product)
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
		links.add(linkTo(methodOn(ProductController.class).getProductList())
			.withRel(relProvider.getItemResourceRelFor(ProductGetCollectionResource.class)));
		return links;
	}
}
