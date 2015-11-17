package congo.order.resource;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;

@Relation(value = "order", collectionRelation = "order")
public class OrderGetResource extends Resources<ResourceSupport>
{
	private final BigDecimal total;
	private final String creditCardNumber;
	private final String address;
	private final Date time;


	public OrderGetResource(
		BigDecimal total,
		String creditCardNumber,
		String address,
		Date time,
		Iterable<ResourceSupport> content,
		Iterable<Link> links)
	{
		super(content, links);
		this.total = total;
		this.creditCardNumber = creditCardNumber;
		this.address = address;
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


	public String getAddress()
	{
		return address;
	}


	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	public Date getTime()
	{
		return time;
	}
}
