package basic.springboot.simple.service;

import basic.springboot.simple.entity.Address;
import basic.springboot.simple.model.DTO.AddressDTO;

import java.util.Optional;

/**
 * Service Interface : Address
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

public interface AddressService {
    public Address save(AddressDTO addressDTO);
}
