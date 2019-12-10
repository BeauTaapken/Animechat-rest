package animechat.rest.api.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userfriend")
@EntityListeners(AuditingEntityListener.class)
public class Friend {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID", required = true)
    private int id;

    @NotNull
    @ApiModelProperty(notes = "The saved emailadres of a user", required = true)
    private String user;

    @NotNull
    @ApiModelProperty(notes = "The saved emailadres of a friend", required = true)
    private String friend;

    public Friend(){

    }

    public int GetId(){ return id; }
    public void SetId(int id){ this.id = id; }
    public String GetUser(){ return user; }
    public void SetUser(String user){ this.user = user; }
    public String GetFriend(){ return friend; }
    public void SetFriend(String friend){ this.friend = friend; }
}
