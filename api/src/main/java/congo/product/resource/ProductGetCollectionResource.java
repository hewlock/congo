package congo.product.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "products", collectionRelation = "products")
public class ProductGetCollectionResource extends Resources<ResourceSupport>
{
	public ProductGetCollectionResource(Iterable<ResourceSupport> content, Iterable<Link> links)
	{
		super(content, links);
	}
}
