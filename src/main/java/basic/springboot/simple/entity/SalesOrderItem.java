package basic.springboot.simple.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity : SalesOrderItem
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Entity
@Table(name="sales_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="sales_order_id", referencedColumnName = "id")
    private SalesOrder salesOrder;

    @Column(name="name")
    private String name;

    @Column(name="sku")
    private String sku;

    @Column(name="qty_ordered")
    private Integer qtyOrdered;

    @Column(name="unit_price")
    private Double unitPrice;

    @Column(name="total_amount")
    private Double totalAmount;

    @Column(name="created")
    private Date created;

    @Column(name="modified")
    private Date modified;
}
