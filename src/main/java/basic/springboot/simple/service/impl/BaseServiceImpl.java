package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.entity.User;
import basic.springboot.simple.repository.TenantRepository;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class BaseServiceImpl implements BaseService {

    private final HttpServletRequest request;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    public BaseServiceImpl(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Integer getTenantId() {
        return Integer.parseInt(request.getAttribute("tenantId").toString());
    }

    @Override
    public Tenant getTenant() {
        Optional<Tenant> tenant = tenantRepository.findById(Integer.parseInt(request.getAttribute("tenantId").toString()));
        return (tenant.isPresent() ? tenant.get() : null);
    }

    @Override
    public Integer getCurrentUserId() {
        return Integer.parseInt(request.getAttribute("userId").toString());
    }

    @Override
    public User getCurrentUser() {
        Optional<User> user = userRepository.findById(Integer.parseInt(request.getAttribute("userId").toString()));
        return (user.isPresent() ? user.get() : null);
    }
}
