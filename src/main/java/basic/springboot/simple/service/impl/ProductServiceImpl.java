package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Product;
import basic.springboot.simple.model.DTO.ProductDTO;
import basic.springboot.simple.repository.ProductRepository;
import basic.springboot.simple.service.BaseService;
import basic.springboot.simple.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service Implementation : Product
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product save(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);

        product.setUser(baseService.getCurrentUser());
        product.setTenant(baseService.getTenant());
        product.setCreated(new Date());
        product.setModified(new Date());

        return productRepository.save(product);
    }
}
