package shop.product;

import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class ProductResource extends ResourceSupport
{
	private final String name;
	private final String description;
	private final BigDecimal price;

	@JsonCreator
	public ProductResource(
			@JsonProperty("name") String name,
			@JsonProperty("price") BigDecimal price,
			@JsonProperty("description") String description)
	{
		this.name = name;
		this.price = price;
		this.description = description;
	}


	public ProductResource(Product product)
	{
		this(product.getName(), product.getPrice(), product.getDescription());
	}


	public String getName()
	{
		return name;
	}


	public BigDecimal getPrice()
	{
		return price;
	}


	public String getDescription()
	{
		return description;
	}
}
