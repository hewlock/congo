package congo.order;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("orders")
public class OrderListResource extends EmbeddedResourceSupport
{
}
