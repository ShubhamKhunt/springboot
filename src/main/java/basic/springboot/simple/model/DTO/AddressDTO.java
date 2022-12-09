package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * DTO : Address
 * Note : address payload for saving address
 * Date : 08-12-2022
 * @author : Shubham Khunt
 * */

@Data
public class AddressDTO {

    @NotEmpty
    @JsonProperty("contact_id")
    private String contactId;

    @NotEmpty
    @JsonProperty("address")
    private String address;

    @JsonProperty("landmark")
    private String landmark;

    @NotEmpty
    @JsonProperty("city")
    private String city;

    @NotEmpty
    @JsonProperty("state")
    private String state;

    @NotEmpty
    @JsonProperty("country")
    private String country;

    @NotEmpty
    @JsonProperty("pincode")
    private String pincode;
}
