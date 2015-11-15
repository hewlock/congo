package congo.product.resource;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("products")
public class ProductGetCollectionResource extends EmbeddedResourceSupport
{
}
