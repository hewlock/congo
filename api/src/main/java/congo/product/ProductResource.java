package congo.product;

import java.math.BigDecimal;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("product")
public class ProductResource extends EmbeddedResourceSupport
{
	private final String name;
	private final String description;
	private final BigDecimal price;


	public ProductResource(String name, BigDecimal price, String description)
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
