package animechat.rest.api.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @NotNull
    @Email
    @ApiModelProperty(notes = "The saved unique email of a user", required = true)
    private String email;

    @NotNull
    @ApiModelProperty(notes = "The saved name of a user", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(notes = "The saved image URL of a user", required = true)
    private String imgUrl;

    /**
     * Used for normal use
     */
    public User(){
    }

    /**
     * Used for unitTests and intergration tests
     * @param email The email of the user that has logged in
     * @param name The name of the user that has logged in
     * @param imgUrl The image url of the user that has logged in
     */
    public User(String email, String name, String imgUrl){
        this.email = email;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public String getImgUrl(){ return imgUrl; }
    public void setImgUrl(String imgUrl){ this.imgUrl = imgUrl; }
}
