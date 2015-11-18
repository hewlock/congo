package congo.product.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:products", collectionRelation = "congo:products")
public class ProductCollectionResource extends Resources<ResourceSupport>
{
	public ProductCollectionResource(Iterable<ResourceSupport> content, Iterable<Link> links)
	{
		super(content, links);
	}
}
