package basic.springboot.simple.controller;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.repository.TenantRepository;
import basic.springboot.simple.service.BaseService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityManager;
import java.util.Objects;
import java.util.Optional;

public class BaseController {
    /*@Autowired
    private EntityManager entityManager;

    @Autowired
    private BaseService baseService;

    @Autowired
    private TenantRepository tenantRepository;

    private void activateFilter() {
        Session session = entityManager.unwrap(Session.class);

        if(Objects.nonNull(baseService.getTenantId())){
            Optional<Tenant> tenant = tenantRepository.findById(baseService.getTenantId());

            if(tenant.isPresent()){
                Filter filter = session.enableFilter("userFilter");
                filter.setParameter("isDeleted", 0);
                filter.setParameter("tenantId", baseService.getTenantId());
            }
        }
    } */

    /**
     * Filter : userFilter [ON User model]
     * Exclude deleted users from the users
     * */
    /*@ModelAttribute
    public void initFilter() {
        activateFilter();
    }*/
}
