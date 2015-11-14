package congo.cart;

import org.springframework.hateoas.core.Relation;

import congo.EmbeddedResourceSupport;

@Relation("cart-item")
public class CartItemResource extends EmbeddedResourceSupport
{
}
