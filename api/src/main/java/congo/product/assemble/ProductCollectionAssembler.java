package congo.product.assemble;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.product.Product;
import congo.product.ProductController;
import congo.product.resource.ProductCollectionResource;
import congo.product.resource.ProductResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ProductCollectionAssembler implements ResourceAssembler<Collection<Product>, ProductCollectionResource>
{
	@Autowired
	RelProvider relProvider;

	@Autowired
	ProductAssembler productAssembler;


	@Override
	public ProductCollectionResource toResource(Collection<Product> products)
	{
		return new ProductCollectionResource(
			getEmbeds(products),
			getLinks());
	}


	private Collection<Link> getLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ProductController.class).getProductList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(ProductController.class), "id")),
			relProvider.getItemResourceRelFor(ProductResource.class)));
		return links;
	}


	private Collection<ResourceSupport> getEmbeds(Collection<Product> products)
	{
		Collection<ResourceSupport> resources = new ArrayList<ResourceSupport>();
		for (Product product : products)
		{
			resources.add(productAssembler.toResource(product));
		}
		return resources;
	}
}
