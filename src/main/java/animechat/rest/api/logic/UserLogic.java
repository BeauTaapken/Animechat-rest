package animechat.rest.api.logic;

import animechat.rest.api.model.User;
import animechat.rest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogic {
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    private UserRepository userRepo;
    private LoggerLogic loggerLogic;

    @Autowired
    public UserLogic(UserRepository userRepo, LoggerLogic loggerLogic){
        this.userRepo = userRepo;
        this.loggerLogic = loggerLogic;
    }
    // </editor-fold>

    public void addUser(User user){
        try{
            userRepo.save(user);
        }
        catch(Exception e){
            loggerLogic.errorLogging(String.valueOf(e));
        }
    }
}
