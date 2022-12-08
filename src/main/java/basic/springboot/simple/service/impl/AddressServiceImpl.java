package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Address;
import basic.springboot.simple.entity.User;
import basic.springboot.simple.exception.InternalServerException;
import basic.springboot.simple.exception.NoDataFoundException;
import basic.springboot.simple.model.DTO.AddressDTO;
import basic.springboot.simple.repository.AddressRepository;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.AddressService;
import basic.springboot.simple.service.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Service Impl : Address
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Address save(AddressDTO addressDTO) {

        Address address = null;

        try{
            User currentUser = baseService.getCurrentUser();

            if(Objects.isNull(currentUser)){
                throw new NoDataFoundException("User not found");
            }

            address = modelMapper.map(addressDTO, Address.class);

            address.setUser(currentUser);
            address.setCreated(new Date());
            address.setModified(new Date());

            addressRepository.save(address);
        } catch (Exception e){
            throw new InternalServerException("Something went wrong while saving object!");
        }

        return address;
    }
}
