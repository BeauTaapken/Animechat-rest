package animechat.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userfriend")
@EntityListeners(AuditingEntityListener.class)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID", required = true)
    private int id;

    @NotNull
    @Email
    @JsonProperty("user")
    @ApiModelProperty(notes = "The saved emailadres of a user", required = true)
    private String user;

    @NotNull
    @Email
    @JsonProperty("friend")
    @ApiModelProperty(notes = "The saved emailadres of a friend", required = true)
    /**
     * The name of this private variable is friend, because of the name of the tableitem
     * it refers to in the database. This shouldn't be changed to favor sonarqube unless
     * the naming in the database also changes
     */
    private String friend;

    /**
     * Used for normal use
     */
    public Friend(){
    }

    /**
     * Used for unitTests and intergration tests
     * @param user The email of the user that has added the friend
     * @param friendEmail The email of the friend that the user has added
     */
    public Friend(String user, String friendEmail){
        this.user = user;
        this.friend = friendEmail;
    }

    /**
     * Used for unitTests and intergration tests
     * @param id A unique id that is given to every combination of userEmail friendEmail in the database
     * @param user The email of the user that has added the friend
     * @param friendEmail The email of the friend that the user has added
     */
    public Friend(int id, String user, String friendEmail){
        this.id = id;
        this.user = user;
        this.friend = friendEmail;
    }

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }
    public String getUserEmail(){ return user; }
    public void setUserEmail(String user){ this.user = user; }
    public String getFriendEmail(){ return friend; }
    public void setFriendEmail(String friend){ this.friend = friend; }
}
