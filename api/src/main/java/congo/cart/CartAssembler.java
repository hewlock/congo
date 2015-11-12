package congo.cart;

import java.util.Collection;

public interface CartAssembler
{
	CartItemResource assemble(CartItem item);

	CartItemListResource assemble(Collection<CartItem> items);
}
