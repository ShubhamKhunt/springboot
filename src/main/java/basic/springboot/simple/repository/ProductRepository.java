package basic.springboot.simple.repository;

import basic.springboot.simple.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
