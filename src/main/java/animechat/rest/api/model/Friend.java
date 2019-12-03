package animechat.rest.api.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userfriend")
@EntityListeners(AuditingEntityListener.class)
public class Friend {
    @Id
    @NotNull
    private int id;

    @NotNull
    private String user;

    @NotNull
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
