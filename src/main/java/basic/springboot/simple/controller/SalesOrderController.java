package basic.springboot.simple.controller;


import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.SalesOrderDTO;
import basic.springboot.simple.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * RestController : SalesOrder
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@RestController
@RequestMapping(path = "/salesorder")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping(path="/")
    public ResponseEntity<Object> save(@Valid  @RequestBody SalesOrderDTO salesOrderDTO){
        return ApiResponse.send(salesOrderService.save(salesOrderDTO));
    }
}
