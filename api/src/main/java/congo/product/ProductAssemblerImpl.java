package congo.product;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import congo.EmbeddedResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
class ProductAssemblerImpl implements ProductAssembler
{
	@Override
	public ProductResource assemble(Product product)
	{
		ProductResource resource = new ProductResource(product.getName(), product.getPrice(), product.getDescription());
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


	@Override
	public ProductListResource assemble(Collection<Product> products)
	{
		ProductListResource resource = new ProductListResource();
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
			resources.add(assemble(product));
		}
		return resources;
	}
}
