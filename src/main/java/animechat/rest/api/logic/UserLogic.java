package animechat.rest.api.logic;

import animechat.rest.api.model.User;
import animechat.rest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLogic {
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    private UserRepository userRepo;

    @Autowired
    public UserLogic(UserRepository userRepo){
        this.userRepo = userRepo;
    }
    // </editor-fold>

    public void addUser(User user){
        try{
            userRepo.save(user);
        }
        catch(Exception e){
            LoggerLogic.errorLogging(String.valueOf(e));
        }
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
}
