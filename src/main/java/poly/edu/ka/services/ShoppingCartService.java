package poly.edu.ka.services;

import java.util.Collection;

import poly.edu.ka.domain.CartItem;

public interface ShoppingCartService {

	double getAmount();

	int getCount();

	Collection<CartItem> getAllItems();

	void clear();

	CartItem update(Long productId, int qty);

	void remove(Long id);

	void add(CartItem item);






}
