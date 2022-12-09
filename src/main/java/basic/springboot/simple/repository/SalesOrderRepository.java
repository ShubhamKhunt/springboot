package basic.springboot.simple.repository;

import basic.springboot.simple.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository : SalesOrderRepository
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
}
