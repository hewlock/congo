package congo.cart;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import congo.product.ProductResource;

@Relation("cart")
public class CartItemListResource
{
	private final Collection<ResourceSupport> cartItems = new HashSet<ResourceSupport>();
	private final Collection<ResourceSupport> products = new HashSet<ResourceSupport>();
	private final BigDecimal total;


	public CartItemListResource(BigDecimal total)
	{
		this.total = total;
	}


	public int getCount()
	{
		return cartItems.size();
	}


	public BigDecimal getTotal()
	{
		return total;
	}


	public void embed(CartItemResource item)
	{
		cartItems.add(item);
	}


	public void embed(ProductResource item)
	{
		products.add(item);
	}


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("_embedded")
	public Map<String, Collection<ResourceSupport>> getEmbedded()
	{
		Map<String, Collection<ResourceSupport>> embedded = new HashMap<String, Collection<ResourceSupport>>(2);
		embedded.put("cart-item", cartItems);
		embedded.put("products", products);
		return embedded;
	}
}
