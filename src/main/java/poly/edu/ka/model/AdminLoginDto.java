package poly.edu.ka.model;


import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDto {
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
	
	private Boolean rememberMe = false; 
}
