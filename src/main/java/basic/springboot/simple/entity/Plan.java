package basic.springboot.simple.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="plans")
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name="allowed_users")
    private Integer allowedUsers;

    @Column(name="allowed_orders")
    private Integer allowedOrders;

}
