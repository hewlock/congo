package congo.product.assemble;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.EmbeddedResourceSupport;
import congo.product.Product;
import congo.product.ProductController;
import congo.product.resource.ProductGetCollectionResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class ProductGetCollectionAssembler implements ResourceAssembler<Collection<Product>, ProductGetCollectionResource>
{
	@Autowired
	ProductGetAssembler productGetAssembler;


	@Override
	public ProductGetCollectionResource toResource(Collection<Product> products)
	{
		ProductGetCollectionResource resource = new ProductGetCollectionResource();
		resource.add(getProductListLinks());
		resource.embed(getProductListEmbeds(products));
		return resource;
	}


	private Collection<Link> getProductListLinks()
	{
		Collection<Link> links = new ArrayList<Link>();
		links.add(linkTo(methodOn(ProductController.class).getProductList()).withSelfRel());
		links.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(ProductController.class), "id")), "product"));
		return links;
	}


	private Collection<EmbeddedResourceSupport> getProductListEmbeds(Collection<Product> products)
	{
		Collection<EmbeddedResourceSupport> resources = new ArrayList<EmbeddedResourceSupport>();
		for (Product product : products)
		{
			resources.add(productGetAssembler.toResource(product));
		}
		return resources;
	}
}
