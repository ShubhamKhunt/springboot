package basic.springboot.simple.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.Date;

/**
 * Date : 23-11-2022
 * User Entity
 * */
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamicUpdate
/*@FilterDef(name="userFilter",
        parameters= {
                @ParamDef(name="isDeleted", type="int"),
                @ParamDef(name="tenantId", type="int")
        },
        defaultCondition = "tenant_id = :tenantId AND is_deleted = :isDeleted"
)
@Filter(name = "userFilter")*/
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "role_id")
    private int roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="tenant_id", referencedColumnName = "id")
    private Tenant tenant;

    @JsonIgnore
    @Column(name="refresh_token")
    private String refreshToken;

    @JsonIgnore
    @Column(name="invite")
    private Integer invite;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", roleId=" + roleId +
                ", refreshToken=" + refreshToken +
                ", lastLogin=" + lastLogin +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
