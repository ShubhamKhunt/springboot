package basic.springboot.simple.repository;

import basic.springboot.simple.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository : Contact
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
