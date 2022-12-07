package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class TenantDTO {

    @Size(min=3, message = "Minimum 3 chars required")
    @Size(max=15, message = "Maximum 15 chars allowed")
    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;

    @Size(min=3, message = "Minimum 3 chars required")
    @Size(max=15, message = "Maximum 15 chars allowed")
    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty
    @Email
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @Size(min=8, message = "Password strength should be between 8 to 16")
    @Size(max=16, message = "Password strength should be between 8 to 16")
    @JsonProperty("password")
    private String password;

    @NotEmpty
    @JsonProperty("plan_id")
    private String planId;
}
