package basic.springboot.simple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity : Contact [Table = contacts]
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@Entity
@Table(name="contacts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="contact_no")
    private String contactNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;

    @Column(name="created")
    private Date created;

    @Column(name="modified")
    private Date modified;
}
