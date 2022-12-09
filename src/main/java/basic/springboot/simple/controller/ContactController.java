package basic.springboot.simple.controller;

import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.ContactDTO;
import basic.springboot.simple.service.ContactService;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Controller : Contact
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@RestController
@RequestMapping(path="/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody ContactDTO contactDTO){
        return ApiResponse.send(contactService.save(contactDTO));
    }
}
