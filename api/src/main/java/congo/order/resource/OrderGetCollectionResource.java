package congo.order.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:orders", collectionRelation = "congo:orders")
public class OrderGetCollectionResource extends Resources<ResourceSupport>
{
	public OrderGetCollectionResource(Iterable<ResourceSupport> content, Iterable<Link> links)
	{
		super(content, links);
	}
}
