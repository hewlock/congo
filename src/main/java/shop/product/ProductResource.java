package shop.product;

import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Relation("product")
public class ProductResource extends ResourceSupport
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
