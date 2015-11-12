package congo.product;

import java.math.BigDecimal;

public class Product
{
	private final long id;
	private final String name;
	private final String description;
	private final BigDecimal price;


	public Product(long id, String name, BigDecimal price, String description)
	{
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}


	public long getId()
	{
		return id;
	}


	public String getName()
	{
		return name;
	}


	public BigDecimal getPrice()
	{
		return price;
	}


	public String getDescription()
	{
		return description;
	}
}
