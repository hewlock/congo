package congo.order.resource;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;

import congo.EmbeddedResourceSupport;

@Relation("order")
public class OrderGetResource extends EmbeddedResourceSupport
{
	private final BigDecimal total;
	private final String creditCardNumber;
	private final String address;
	private final Date time;


	public OrderGetResource(
		BigDecimal total,
		String creditCardNumber,
		String address,
		Date time)
	{
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
