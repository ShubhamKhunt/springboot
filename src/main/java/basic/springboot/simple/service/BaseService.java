package basic.springboot.simple.service;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.entity.User;

public interface BaseService {
    public Integer getTenantId();

    public Tenant getTenant();

    public Integer getCurrentUserId();

    public User getCurrentUser();
}
