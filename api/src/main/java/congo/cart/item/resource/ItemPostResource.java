package congo.cart.item.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

@Relation(value = "congo:shopping-cart-item", collectionRelation = "congo:shopping-cart-item")
public class ItemPostResource extends ResourceSupport
{
	private String product;


	public String getProduct()
	{
		return product;
	}


	@JsonProperty("congo:product")
	public void setProduct(String product)
	{
		this.product = product;
	}
}
