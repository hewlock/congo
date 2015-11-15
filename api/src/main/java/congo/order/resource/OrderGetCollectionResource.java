package congo.order.resource;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("orders")
public class OrderGetCollectionResource extends EmbeddedResourceSupport
{
}
