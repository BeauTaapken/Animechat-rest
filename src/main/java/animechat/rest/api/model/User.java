package animechat.rest.api.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @NotNull
    @ApiModelProperty(notes = "The saved unique email of a user", required = true)
    private String email;

    @NotNull
    @ApiModelProperty(notes = "The saved name of a user", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(notes = "The saved image URL of a user", required = true)
    private String imgUrl;

    public User(){

    }

    public String GetEmail(){ return email; }
    public void SetEmail(String email){ this.email = email; }
    public String GetName(){ return name; }
    public void SetName(String name){ this.name = name; }
    public String GetImgUrl(){ return imgUrl; }
    public void setImgUrl(String imgUrl){ this.imgUrl = imgUrl; }
}
