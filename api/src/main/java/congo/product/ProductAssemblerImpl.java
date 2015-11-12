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
		ProductListResource productListResource = new ProductListResource();
		for(Product product : products)
		{
			ProductResource productResource = assemble(product);
			productListResource.embed(productResource);
		}

		productListResource.add(linkTo(methodOn(ProductController.class).getProductList()).withSelfRel());
		productListResource.add(new Link(new UriTemplate(String.format("%s/{%s}", linkTo(ProductController.class), "productId")), "product"));

		return productListResource;
	}
}
