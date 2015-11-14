package congo.cart;

import java.util.Collection;

public interface CartService
{
	CartItem getCartItem(long itemId);

	Collection<CartItem> getAllCartItems();

	CartItem saveItem(CartItem item);

	CartItem deleteItem(long itemId);

	void clear();
}
