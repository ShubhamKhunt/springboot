package basic.springboot.simple.controller;

import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.AddressDTO;
import basic.springboot.simple.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody AddressDTO addressDTO){
        return ApiResponse.send(addressService.save(addressDTO));
    }
}
