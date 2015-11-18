package congo.cart.item.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:shopping-cart-item", collectionRelation = "congo:shopping-cart-item")
public class ItemResource extends Resources<ResourceSupport>
{
	public ItemResource(Iterable<ResourceSupport> content, Iterable<Link> links)
	{
		super(content, links);
	}
}
