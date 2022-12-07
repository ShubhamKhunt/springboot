package basic.springboot.simple.controller;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.TenantDTO;
import basic.springboot.simple.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    // Autowire Tenant Service
    private TenantService tenantService;

    public TenantController(){}

    @Autowired
    public TenantController(TenantService tenantService){
        this.tenantService = tenantService;
    }

    /**
    * Date : 22-11-2022
    * API : Tenant Registration
    * */
    @PostMapping(path ="/register")
    public ResponseEntity<Object> addTenant(@Valid @RequestBody TenantDTO tenantDTO){
        Tenant tenant = tenantService.register(tenantDTO);
        return ApiResponse.send(tenant);
    }
}
