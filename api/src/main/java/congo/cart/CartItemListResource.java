package congo.cart;

import java.math.BigDecimal;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("cart")
public class CartItemListResource extends EmbeddedResourceSupport
{
	private final BigDecimal total;


	public CartItemListResource(BigDecimal total)
	{
		this.total = total;
	}


	public BigDecimal getTotal()
	{
		return total;
	}
}
