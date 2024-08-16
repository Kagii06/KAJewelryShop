package poly.edu.ka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
	private Long productId;
	private String name;
	private int quantity = 1;
	private double unitPrice;
	private String image;

}
