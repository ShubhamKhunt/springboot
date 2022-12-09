package basic.springboot.simple.service;

import basic.springboot.simple.entity.Contact;
import basic.springboot.simple.model.DTO.ContactDTO;

import java.util.Optional;

/**
 * Service Interface : Contact
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

public interface ContactService {
    public Contact save(ContactDTO contactDTO);
}
