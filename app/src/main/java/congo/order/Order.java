package congo.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import congo.cart.CartItem;
import congo.product.Product;

public class Order
{
	private final long id;
	private final String creditCardNumber;
	private final String address;
	private final Collection<Product> products;
	private final Date time;


	public Order(String creditCardNumber, String address)
	{
		this(0, new ArrayList<Product>(), creditCardNumber, address, new Date());
	}


	Order(long id, Collection<Product> products, String creditCardNumber, String address, Date time)
	{
		this.id = id;
		this.products = products;
		this.creditCardNumber = creditCardNumber;
		this.address = address;
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


	public String getAddress()
	{
		return address;
	}


	public Date getTime()
	{
		return time;
	}


	public void addAll(Collection<CartItem> items)
	{
		for (CartItem item : items)
		{
			products.add(item.getProduct());
		}
	}
}
