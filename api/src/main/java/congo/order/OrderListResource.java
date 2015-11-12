package congo.order;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import congo.product.ProductResource;

@Relation("orders")
public class OrderListResource extends ResourceSupport
{
	private final Collection<ResourceSupport> orders = new HashSet<ResourceSupport>();
	private final Collection<ResourceSupport> products = new HashSet<ResourceSupport>();


	public int getCount()
	{
		return orders.size();
	}


	public void embed(OrderResource resource)
	{
		orders.add(resource);
	}


	public void embed(ProductResource resource)
	{
		products.add(resource);
	}


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("_embedded")
	public Map<String, Collection<ResourceSupport>> getEmbedded()
	{
		Map<String, Collection<ResourceSupport>> embedded = new HashMap<String, Collection<ResourceSupport>>(2);
		embedded.put("order", orders);
		embedded.put("products", products);
		return embedded;
	}
}
