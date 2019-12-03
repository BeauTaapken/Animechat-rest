package animechat.rest.api.model;

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
    private String email;

    @NotNull
    private String name;

    public User(){

    }

    public String GetEmail(){ return email; }
    public void SetEmail(String email){ this.email = email; }
    public String GetName(){ return name; }
    public void SetName(String name){ this.name = name; }
}
