package animechat.rest.api.logic;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import animechat.rest.api.repository.FriendRepository;
import animechat.rest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class FriendLogic {
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    private UserRepository userRepo;
    private FriendRepository friendRepo;

    @Autowired
    public FriendLogic(UserRepository userRepo, FriendRepository friendRepo){
        this.userRepo = userRepo;
        this.friendRepo = friendRepo;
    }
    // </editor-fold>

    public List<User> getUserFriends(String userEmail){
        try{
            List<Friend> friends = friendRepo.findAll();
            List<User> users = userRepo.findAll();

            List<String> friendEmails = makeFriendEmailList(userEmail, friends);

            return users.stream().filter(x -> friendEmails.contains(x.getEmail())).collect(Collectors.toList());
        }
        catch (Exception e){
            LoggerLogic.errorLogging(String.valueOf(e));
            return new ArrayList<>();
        }
    }

    public List<User> getNonFriends(String userEmail){
        try{
            List<Friend> friends = friendRepo.findAll();
            List<User> users = userRepo.findAll();

            List<String> friendEmails = makeFriendEmailList(userEmail, friends);
            friendEmails.add(userEmail);
            return users.stream().filter(x -> !friendEmails.contains(x.getEmail())).collect(Collectors.toList());
        }
        catch (Exception e){
            LoggerLogic.errorLogging(String.valueOf(e));
            return new ArrayList<>();
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
