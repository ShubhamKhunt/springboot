package basic.springboot.simple.service;

import basic.springboot.simple.entity.Tenant;

public interface BaseService {
    public Integer getTenantId();

    public Tenant getTenant();
}
