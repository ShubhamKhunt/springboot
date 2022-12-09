package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ContactDTO {

    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @JsonProperty("contact_no")
    private String contactNo;
}
