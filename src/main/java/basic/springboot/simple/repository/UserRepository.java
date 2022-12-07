package basic.springboot.simple.repository;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    public Optional<User> findById(Integer userId);

    @Query(value="SELECT u FROM User u WHERE u.tenant = ?1")
    public Optional<List<User>> getUsersByTenant(Tenant tenant, Pageable pageable);

    public Optional<User> findByIdAndTenant(Integer userId, Tenant tenant);

    public User findByRefreshToken(String refreshToken);

    @Query(value="SELECT count(*) FROM User u where u.tenant = ?1")
    public Integer getNoOfUsersOfTenant(Tenant tenant);
}

