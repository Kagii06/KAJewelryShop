package poly.edu.ka.service.Impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import poly.edu.ka.domain.CartItem;
import poly.edu.ka.services.ShoppingCartService;
@SessionScope
@Service
public class ShoppingCartServiceImpl  implements ShoppingCartService{
	Map<Long, CartItem> maps = new HashMap<>();

	@Override
	public void add(CartItem item)
	{
		CartItem cartItem = maps.get(item.getProductId());
		if(cartItem == null)
		{
			maps.put(item.getProductId(), item);
		}else
		{
			cartItem.setQuantity(getCount());
		}
	}
	@Override
	public void remove(Long id)
	{
		maps.remove(id);
		
	}
	@Override
	public CartItem update(Long productId, int qty)
	{
		CartItem cartItem = maps.get(productId);
		cartItem.setQuantity(qty);
		return cartItem;
	}
	@Override
	public void clear()
	{
		maps.clear();
	}
	@Override
	public Collection<CartItem> getAllItems()
	{
		return maps.values();
	}
	@Override
	public int getCount()
	{
		return maps.values().size();
	}
	@Override
	public double getAmount()
	{
		return maps.values().stream().mapToDouble(item-> item.getQuantity() * item.getUnitPrice()).sum();
	}
	
}
