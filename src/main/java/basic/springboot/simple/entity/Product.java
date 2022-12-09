package basic.springboot.simple.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity : Product
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "category")
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;
}
