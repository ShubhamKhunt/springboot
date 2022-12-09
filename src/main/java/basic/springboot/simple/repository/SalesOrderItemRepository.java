package basic.springboot.simple.repository;

import basic.springboot.simple.entity.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository : SalesOrderItem
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Integer> {
}
