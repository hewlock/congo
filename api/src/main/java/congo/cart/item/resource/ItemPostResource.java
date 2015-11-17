package congo.cart.item.resource;

import org.springframework.hateoas.ResourceSupport;

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
