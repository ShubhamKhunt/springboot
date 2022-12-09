package basic.springboot.simple.controller;

import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.ProductDTO;
import basic.springboot.simple.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Controller : Product
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/")
    public ResponseEntity<Object> save(@Valid @RequestBody ProductDTO productDTO){
        return ApiResponse.send(productService.save(productDTO));
    }
}
