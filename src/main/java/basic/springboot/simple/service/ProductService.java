package basic.springboot.simple.service;

import basic.springboot.simple.entity.Product;
import basic.springboot.simple.model.DTO.ProductDTO;

/**
 * Service Interface : Product
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

public interface ProductService {
    public Product save(ProductDTO productDTO);
}
