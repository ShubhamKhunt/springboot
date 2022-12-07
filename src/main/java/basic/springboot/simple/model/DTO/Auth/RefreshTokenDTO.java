package basic.springboot.simple.model.DTO.Auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenDTO {
    @NotEmpty
    @JsonProperty("refresh_token")
    private String refreshToken;
}
