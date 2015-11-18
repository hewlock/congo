package congo.order.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:order", collectionRelation = "congo:order")
public class OrderForm extends ResourceSupport
{
	private String creditCardNumber;
	private String address;


	public String getCreditCardNumber()
	{
		return creditCardNumber;
	}


	public void setCreditCardNumber(String creditCardNumber)
	{
		this.creditCardNumber = creditCardNumber;
	}


	public String getAddress()
	{
		return address;
	}


	public void setAddress(String address)
	{
		this.address = address;
	}
}
