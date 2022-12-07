package basic.springboot.simple.repository;

import basic.springboot.simple.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    public Optional<Tenant> findByEmail(String email);

    @Query(value = "SELECT p.allowedUsers FROM Tenant t JOIN Plan p ON t.planId = p.id WHERE t.id = ?1")
    public Integer getAllowedUsersOfPlan(int tenantId);
}
