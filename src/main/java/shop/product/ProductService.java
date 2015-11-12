package shop.product;

import java.util.Collection;

public interface ProductService
{
	Product findProduct(long productId);

	Collection<Product> findAllProducts();

	void saveProduct(Product product);
}
