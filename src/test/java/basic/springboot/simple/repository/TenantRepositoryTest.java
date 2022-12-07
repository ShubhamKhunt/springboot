package basic.springboot.simple.repository;

import basic.springboot.simple.entity.Tenant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.annotation.TestAnnotationUtils;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TenantRepositoryTest {

    @Autowired
    private TenantRepository tenantRepository;

    @Test
    @Rollback(value = true)
    public void saveTenantTest(){
        Tenant tenant = Tenant.builder()
                .firstName("Test")
                .lastName("Tenant")
                .email("test.tenant@email.com")
                .planId(3)
                .created(new Date())
                .modified(new Date())
                .build();

        tenantRepository.save(tenant);

        System.out.println("Tenant ID : " + tenant.getId());

        Assertions.assertTrue(tenant.getId() > 0);
    }
}
