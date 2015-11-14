package congo.product;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("products")
public class ProductListResource extends EmbeddedResourceSupport
{
}
