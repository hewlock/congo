package congo.order;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;

import congo.EmbeddedResourceSupport;

@Relation("order")
public class OrderResource extends EmbeddedResourceSupport
{
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


	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	public Date getTime()
	{
		return time;
	}
}
