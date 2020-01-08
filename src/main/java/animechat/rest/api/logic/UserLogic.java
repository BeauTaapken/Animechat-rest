package animechat.rest.api.logic;

import animechat.rest.api.model.User;
import animechat.rest.api.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogic {
    private UserRepository userRepo;

    @Autowired
    public UserLogic(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public void addUser(String user){
        User u = new Gson().fromJson(user, User.class);
        userRepo.save(u);
    }
}
