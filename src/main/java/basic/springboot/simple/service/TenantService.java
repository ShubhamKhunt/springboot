package basic.springboot.simple.service;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.model.DTO.TenantDTO;

public interface TenantService {
    public Tenant register(TenantDTO tenant);
}
