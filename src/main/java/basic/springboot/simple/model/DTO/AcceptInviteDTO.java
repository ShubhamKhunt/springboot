package basic.springboot.simple.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AcceptInviteDTO {

    @NotEmpty
    @JsonProperty("inviteToken")
    private String inviteToken;

    @NotEmpty
    @JsonProperty("password")
    private String password;
}
