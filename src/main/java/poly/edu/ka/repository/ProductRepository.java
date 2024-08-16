package poly.edu.ka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poly.edu.ka.domain.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{

}
