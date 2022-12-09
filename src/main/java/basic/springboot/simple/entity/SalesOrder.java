package basic.springboot.simple.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity : SalesOrder
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Entity
@Table(name="sales_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "shipping_name")
    private String shippingName;

    @Column(name = "shipping_contact")
    private String shippingContact;

    @Column(name = "shipping_email")
    private String shippingEmail;

    @Column(name = "order_amount")
    private Double orderAmount;

    @Column(name = "order_status")
    private Integer orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL)
    private List<SalesOrderItem> salesOrderItems = new ArrayList<>(1);

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;
}
