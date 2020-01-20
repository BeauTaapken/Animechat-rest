package animechat.rest.api.logic;

import animechat.rest.api.interfac.IFriend;
import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import animechat.rest.api.repository.FriendRepository;
import animechat.rest.api.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FriendLogic implements IFriend {
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    private UserLogic userLogic;
    private FriendRepository friendRepo;

    private final Gson gson = new Gson();

    @Autowired
    public FriendLogic(FriendRepository friendRepo, UserLogic userLogic){
        this.friendRepo = friendRepo;
        this.userLogic = userLogic;
    }
    // </editor-fold>

    public String getUserFriends(String userEmail){
        try{
            List<Friend> friends = friendRepo.findAll();
            List<User> users = userLogic.getAllUsers();

            List<String> friendEmails = makeFriendEmailList(userEmail, friends);

            return gson.toJson(users.stream().filter(x -> friendEmails.contains(x.getEmail())).collect(Collectors.toList()));
        }
        catch (Exception e){
            LoggerLogic.errorLogging(String.valueOf(e));
            return gson.toJson("");
        }
    }

    public String getNonFriends(String userEmail){
        try{
            if(!isEmail(userEmail)){
                throw new IllegalArgumentException();
            }

            List<Friend> friends = friendRepo.findAll();
            List<User> users = userLogic.getAllUsers();

            List<String> friendEmails = makeFriendEmailList(userEmail, friends);
            friendEmails.add(userEmail);
            return gson.toJson(users.stream().filter(x -> !friendEmails.contains(x.getEmail())).collect(Collectors.toList()));
        }
        catch (Exception e){
            LoggerLogic.errorLogging(String.valueOf(e));
            return gson.toJson("");
        }
    }

    public void addFriend(Friend friend){
        try{
            friendRepo.save(friend);
        }
        catch (Exception e) {
            LoggerLogic.errorLogging(String.valueOf(e));
        }
    }

    private List<String> makeFriendEmailList(String userEmail, List<Friend> friends){
        //Gets all friends of the specified user
        List<Friend> filteredFriends = friends.stream().filter(x -> x.getUserEmail().equals(userEmail)).collect(Collectors.toList());

        List<String> friendEmails = new ArrayList<>();
        for (Friend f : filteredFriends) {
            friendEmails.add(f.getFriendEmail());
        }

        return friendEmails;
    }

    public boolean isEmail(String userEmail){
        return Pattern.compile("^(.+)@(.+)+\\.(.+)$").matcher(userEmail).matches();
    }
}
