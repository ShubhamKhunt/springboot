package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ReviewDTO {
    @NotEmpty
    @JsonProperty("order_review")
    private String orderReview;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonProperty("sales_order_id")
    private String salesOrderId;
}
