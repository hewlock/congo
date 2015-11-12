package congo.cart;

import java.util.Collection;

public interface CartService
{
	CartItem getCartItem(long itemId);

	Collection<CartItem> getAllCartItems();

	Collection<CartItem> removeAllCartItems();

	CartItem saveItem(CartItem item);

	CartItem deleteItem(long itemId);
}
