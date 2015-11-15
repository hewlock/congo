package congo.product.resource;

import java.math.BigDecimal;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("product")
public class ProductGetResource extends EmbeddedResourceSupport
{
	private final String name;
	private final String description;
	private final BigDecimal price;


	public ProductGetResource(String name, String description, BigDecimal price)
	{
		this.name = name;
		this.description = description;
		this.price = price;
	}


	public String getName()
	{
		return name;
	}


	public String getDescription()
	{
		return description;
	}


	public BigDecimal getPrice()
	{
		return price;
	}
}
