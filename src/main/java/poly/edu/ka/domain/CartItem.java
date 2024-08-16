package poly.edu.ka.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	private Long productId;
	private String name;
	private int quantity = 1;
	private double unitPrice;
	private String image;
}
