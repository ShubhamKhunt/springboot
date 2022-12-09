package basic.springboot.simple.repository;

import basic.springboot.simple.entity.SalesOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class SalesOrderEntityManagerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public SalesOrder save(SalesOrder salesOrder){
        entityManager.persist(salesOrder);
        entityManager.refresh(salesOrder);
        return salesOrder;
    }
}
