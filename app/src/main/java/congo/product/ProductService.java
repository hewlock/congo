package congo.product;

import java.util.Collection;

public interface ProductService
{
	Product getProduct(long productId);

	Collection<Product> getAllProducts();

	void saveProduct(Product product);
}
