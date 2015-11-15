package congo.cart.item.resource;

import java.math.BigDecimal;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("cart")
public class ItemGetCollectionResource extends EmbeddedResourceSupport
{
	private final BigDecimal total;


	public ItemGetCollectionResource(BigDecimal total)
	{
		this.total = total;
	}


	public BigDecimal getTotal()
	{
		return total;
	}
}
