package poly.edu.ka.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
	@Id
	@Column(length = 30)
	private String username;
	
	@Column(length = 60, nullable = false)
	private String password;
	
	@Column(length = 70)
	private String email;
	
	@Column
	private Boolean isAdmin;
	
	@Column
	private Boolean isActive;
}
