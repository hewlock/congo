package shop.product;

import java.util.Collection;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

@Relation("products")
public class ProductListResource extends ResourceSupport
{
	private final Collection<ProductResource> products;


	public ProductListResource(Collection<ProductResource> products)
	{
		this.products = products;
	}


	public int getCount()
	{
		return products.size();
	}


	@JsonProperty
	public Collection<ProductResource> getEmbedded()
	{
		return products;
	}
}
