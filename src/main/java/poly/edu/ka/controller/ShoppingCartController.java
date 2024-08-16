package poly.edu.ka.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.ka.domain.CartItem;
import poly.edu.ka.domain.Product;
import poly.edu.ka.model.ProductDto;
import poly.edu.ka.services.ProductService;
import poly.edu.ka.services.ShoppingCartService;



@Controller
@RequestMapping("site/shopping-cart")
public class ShoppingCartController {
	@Autowired
	ProductService productService;
	@Autowired
	ShoppingCartService cartService;
	
	@GetMapping("views")
	public String viewCart(ModelMap model)
	{
		model.addAttribute("CART_ITEMS", cartService.getAllItems());
		model.addAttribute("TOTAL",cartService.getAmount());
		return "site/cart-item";
	}
	
	@GetMapping("add/{productId}")
	public String addCart(@PathVariable("productId") Long productId)
	{
//		Product product = productService.findById(productId);
		Optional<Product> opt = productService.findById(productId);
		CartItem dto = new CartItem();
		if(opt.isPresent())
		{
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setProductId(entity.getProductId());
			dto.setName(dto.getName());
			dto.setUnitPrice(dto.getUnitPrice());
			dto.setImage(dto.getImage());
			dto.setQuantity(1);
			cartService.add(dto);
		}
		return "redirect:/site/shopping-cart/views";
	}
	
	@GetMapping("clear")
	public String clearCart()
	{
		cartService.clear();
		return "redirect:/site/shopping-cart/views";
	}
	@GetMapping("delete/{productId}")
	public String removeCart(@PathVariable("productId") Long productId)
	{
		cartService.remove(productId);
		return "redirect:/site/shopping-cart/views";
	}
//	@PostMapping("update")
//	public String update(@RequestParam("productId")Long productId, @RequestParam("quantity")Integer quantity)
//	{
//		cartService.update(productId, quantity);
//		return "redirect:/site/shopping-cart/views";
//	}
	@PostMapping("update/{productId}/{quantityOperation}")
	public String updateQuantity(@PathVariable Long productId, @PathVariable String quantityOperation) {
	    Integer quantityChange = 0;
	    if (quantityOperation.equals("+")) {
	        quantityChange = 1;
	    } else if (quantityOperation.equals("-")) {
	        quantityChange = -1;
	    }
	    cartService.update(productId, quantityChange);
	    return "redirect:/site/shopping-cart/views";
	}
}

