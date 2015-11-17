package congo.cart.item.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "shopping-cart-item", collectionRelation = "shopping-cart-item")
public class ItemGetResource extends Resources<ResourceSupport>
{
	public ItemGetResource(Iterable<ResourceSupport> content, Iterable<Link> links)
	{
		super(content, links);
	}
}
