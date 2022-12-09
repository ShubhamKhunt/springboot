package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonProperty("sku")
    private String sku;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @Min(value = 0, message = "Price should not be less than 0")
    @JsonProperty("price")
    private String price;

    @NotEmpty
    @JsonProperty("category")
    private String category;
}
