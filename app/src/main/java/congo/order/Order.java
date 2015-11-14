package congo.order;

import java.util.Collection;
import java.util.Date;

import congo.product.Product;

public class Order
{
	private final long id;
	private final Collection<Product> products;
	private final String creditCardNumber;
	private final Date time;


	public Order(Collection<Product> products, String creditCardNumber)
	{
		this(0, products, creditCardNumber, new Date());
	}


	Order(long id, Collection<Product> products, String creditCardNumber, Date time)
	{
		this.id = id;
		this.products = products;
		this.creditCardNumber = creditCardNumber;
		this.time = time;
	}


	public boolean isValid()
	{
		return !products.isEmpty();
	}


	public long getId()
	{
		return id;
	}


	public Collection<Product> getProducts()
	{
		return products;
	}


	public String getCreditCardNumber()
	{
		return creditCardNumber;
	}


	public Date getTime()
	{
		return time;
	}
}
