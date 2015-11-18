package congo.product.resource;

import java.math.BigDecimal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:product", collectionRelation = "congo:product")
public class ProductGetResource extends Resources<ResourceSupport>
{
	private final String name;
	private final String description;
	private final BigDecimal price;


	public ProductGetResource(
		String name,
		String description,
		BigDecimal price,
		Iterable<ResourceSupport> content,
		Iterable<Link> links)
	{
		super(content, links);
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
