package congo.cart.item.resource;

import java.math.BigDecimal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:shopping-cart", collectionRelation = "congo:shopping-cart")
public class ItemGetCollectionResource extends Resources<ResourceSupport>
{
	private final BigDecimal total;


	public ItemGetCollectionResource(BigDecimal total, Iterable<ResourceSupport> content, Iterable<Link> links)
	{
		super(content, links);
		this.total = total;
	}


	public BigDecimal getTotal()
	{
		return total;
	}
}
