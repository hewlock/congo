package congo.cart;

import congo.product.Product;

public class CartItem
{
	private final long id;
	private final Product product;


	public CartItem(Product product)
	{
		this(0, product);
	}


	CartItem(long id, Product product)
	{
		this.id = id;
		this.product = product;
	}


	public long getId()
	{
		return id;
	}


	public Product getProduct()
	{
		return product;
	}
}
