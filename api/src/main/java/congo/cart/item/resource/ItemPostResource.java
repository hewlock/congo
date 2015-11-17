package congo.cart.item.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "shopping-cart-item", collectionRelation = "shopping-cart-item")
public class ItemPostResource extends ResourceSupport
{
	private String product;


	public String getProduct()
	{
		return product;
	}


	public void setProduct(String product)
	{
		this.product = product;
	}
}
