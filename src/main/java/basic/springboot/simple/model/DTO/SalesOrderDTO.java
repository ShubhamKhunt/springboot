package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Service Interfce : SalesOrder
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Data
public class SalesOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @JsonProperty("order_amount")
    private String orderAmount;

    @NotEmpty
    @JsonProperty("order_status")
    private String orderStatus;

    @NotEmpty
    @JsonProperty("address_id")
    private String addressId;

    @NotEmpty
    @JsonProperty("contact_id")
    private String contactId;

    @NotEmpty
    @JsonProperty("sales_order_items")
    private List<SalesOrderItemDTO> salesOrderItems;
}
