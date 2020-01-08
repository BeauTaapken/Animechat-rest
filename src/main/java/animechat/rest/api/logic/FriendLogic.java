package animechat.rest.api.logic;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import animechat.rest.api.repository.FriendRepository;
import animechat.rest.api.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendLogic {
    private UserRepository userRepo;
    private FriendRepository friendRepo;

    @Autowired
    public FriendLogic(UserRepository userRepo, FriendRepository friendRepo){
        this.userRepo = userRepo;
        this.friendRepo = friendRepo;
    }

    public List<User> getUserFriends(String userEmail){
        List<Friend> friends = friendRepo.findAll();
        List<User> users = userRepo.findAll();

        List<String> friendEmails = makeFriendEmailList(userEmail, friends);

        return users.stream().filter(x -> friendEmails.contains(x.getEmail())).collect(Collectors.toList());
    }

    public List<User> getNonFriends(String userEmail){
        List<Friend> friends = friendRepo.findAll();
        List<User> users = userRepo.findAll();

        List<String> friendEmails = makeFriendEmailList(userEmail, friends);
        friendEmails.add(userEmail);
        return users.stream().filter(x -> !friendEmails.contains(x.getEmail())).collect(Collectors.toList());
    }

    public void addFriend(String friend){
        Friend f = new Gson().fromJson(friend, Friend.class);

        friendRepo.save(f);
    }

    public List<String> makeFriendEmailList(String userEmail, List<Friend> friends){
        //Gets all friends of the specified user
        List<Friend> filteredFriends = friends.stream().filter(x -> x.getUserEmail().equals(userEmail)).collect(Collectors.toList());

        //Adds all the emails of friends of the specified user to a list
        List<String> friendEmails = new ArrayList<>();
        for (Friend f : filteredFriends) {
            friendEmails.add(f.getFriendEmail());
        }

        return friendEmails;
    }
}
