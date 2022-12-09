package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * DTO : SalesOrderItem
 * Date : 09-12-2022
 * @author : Shubham Khunt
 * */

@Data
public class SalesOrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty
    @JsonProperty("product_id")
    private String prodctId;

    @NotEmpty
    @JsonProperty("unit_price")
    private String unitPrice;

    @NotEmpty
    @JsonProperty("qty_ordered")
    private String qtyOrdered;

    @Override
    public String toString() {
        return "SalesOrderItemDTO{" +
                "prodctId='" + prodctId + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qtyOrdered='" + qtyOrdered + '\'' +
                '}';
    }
}
