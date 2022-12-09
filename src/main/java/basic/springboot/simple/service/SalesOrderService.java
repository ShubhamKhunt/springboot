package basic.springboot.simple.service;

import basic.springboot.simple.entity.SalesOrder;
import basic.springboot.simple.model.DTO.SalesOrderDTO;

/**
 * Service Interfce : SalesOrder
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

public interface SalesOrderService {
    public SalesOrder save(SalesOrderDTO salesOrderDTO);
}
