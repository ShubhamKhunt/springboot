package basic.springboot.simple.repository;

import basic.springboot.simple.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository : Address
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
