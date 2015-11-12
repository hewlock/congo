package congo.product;

import java.util.Collection;

public interface ProductAssembler
{
	ProductResource assemble(Product product);

	ProductListResource assemble(Collection<Product> products);
}
