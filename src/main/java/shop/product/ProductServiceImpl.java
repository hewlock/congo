package shop.product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
class ProductServiceImpl implements ProductService
{
	private final Map<Long, Product> products = new HashMap<>();

	public ProductServiceImpl()
	{
		saveProduct(new Product(1, "Fallout 4 - Playstation 4", new BigDecimal("53.50"), "Bethesda Game Studios – their most ambitious game ever."));
		saveProduct(new Product(2, "Fallout 4 - Xbox One", new BigDecimal("59.88"), "Bethesda Game Studios – their most ambitious game ever."));
		saveProduct(new Product(3, "Playstation 4", new BigDecimal("384.90"), "PlayStation 4 500GB Console."));
		saveProduct(new Product(4, "Xbox One", new BigDecimal("333.37"), "Xbox One 500GB Console."));
		saveProduct(new Product(5, "Xbox One - Fallout 4 Bundle", new BigDecimal("399.00"), "Xbox One 1TB Console - Fallout 4 Bundle."));
	}


	@Override
	public Product findProduct(long productId)
	{
		return products.get(productId);
	}


	@Override
	public void saveProduct(Product product)
	{
		products.put(product.getId(), product);
	}
}
