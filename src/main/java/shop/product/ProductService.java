package shop.product;

public interface ProductService
{
	Product findProduct(long productId);

	void saveProduct(Product product);
}
