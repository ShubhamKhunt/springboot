package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Contact;
import basic.springboot.simple.model.DTO.ContactDTO;
import basic.springboot.simple.repository.ContactRepository;
import basic.springboot.simple.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service Impl : Contact
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Contact save(ContactDTO contactDTO) {

        Contact contact = modelMapper.map(contactDTO, Contact.class);

        contact.setCreated(new Date());
        contact.setModified(new Date());

        return contactRepository.save(contact);
    }
}
