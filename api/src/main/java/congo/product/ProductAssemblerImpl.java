package congo.product;

import java.util.Collection;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
class ProductAssemblerImpl implements ProductAssembler
{
	@Override
	public ProductResource assemble(Product product)
	{
		ProductResource productResource = new ProductResource(product.getName(), product.getPrice(), product.getDescription());

		productResource.add(linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());
		productResource.add(linkTo(methodOn(ProductController.class).getProductList()).withRel("products"));

		return productResource;
	}


	@Override
	public ProductListResource assemble(Collection<Product> products)
	{
		ProductListResource listResource = new ProductListResource();
		for (Product product : products)
		{
			ProductResource productResource = assemble(product);
			listResource.embed(productResource);
		}

		listResource.add(linkTo(methodOn(ProductController.class).getProductList()).withSelfRel());
		listResource.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(ProductController.class), "id")), "product"));

		return listResource;
	}
}
