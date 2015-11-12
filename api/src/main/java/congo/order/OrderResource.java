package congo.order;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import congo.product.ProductResource;

@Relation("order")
public class OrderResource extends ResourceSupport
{
	private final Collection<ResourceSupport> products = new HashSet<ResourceSupport>();
	private final BigDecimal total;
	private final String creditCardNumber;
	private final Date time;


	public OrderResource(BigDecimal total, String creditCardNumber, Date time)
	{
		this.total = total;
		this.creditCardNumber = creditCardNumber;
		this.time = time;
	}


	public BigDecimal getTotal()
	{
		return total;
	}


	public String getCreditCardNumber()
	{
		return creditCardNumber;
	}


	public Date getTime()
	{
		return time;
	}


	public void embed(ProductResource item)
	{
		products.add(item);
	}


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("_embedded")
	public Map<String, Collection<ResourceSupport>> getEmbedded()
	{
		Map<String, Collection<ResourceSupport>> embedded = new HashMap<String, Collection<ResourceSupport>>(1);
		if (!products.isEmpty())
		{
			embedded.put("products", products);
		}
		return embedded;
	}
}
