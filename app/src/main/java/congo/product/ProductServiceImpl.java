package congo.product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
class ProductServiceImpl implements ProductService
{
	private final Map<Long, Product> products = new HashMap<Long, Product>();


	public ProductServiceImpl()
	{
		saveProduct(new Product(1, "Fallout 4 - Playstation 4", new BigDecimal("53.50"), "Fallout 4 is a post apocalyptic role-playing game developed by Bethesda Game Studios."));
		saveProduct(new Product(2, "Fallout 4 - Xbox One", new BigDecimal("59.88"), "Fallout 4 is a post apocalyptic role-playing game developed by Bethesda Game Studios"));
		saveProduct(new Product(3, "Playstation 4", new BigDecimal("384.90"), "PlayStation 4 500GB Console."));
		saveProduct(new Product(4, "Xbox One", new BigDecimal("333.37"), "Xbox One 500GB Console."));
		saveProduct(new Product(5, "Weekend Spa Getaway for One", new BigDecimal("399.00"), "All inclusive weekend Spa getaway."));
	}


	@Override
	public Product getProduct(long productId)
	{
		return products.get(productId);
	}


	@Override
	public Collection<Product> getAllProducts()
	{
		return products.values();
	}


	@Override
	public void saveProduct(Product product)
	{
		products.put(product.getId(), product);
	}
}
