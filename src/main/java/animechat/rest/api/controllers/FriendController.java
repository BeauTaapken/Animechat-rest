package animechat.rest.api.controllers;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import animechat.rest.api.repository.FriendRepository;
import animechat.rest.api.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("friend")
@RestController
public class FriendController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FriendRepository friendRepo;

    //Function for getting users friends based on userEmail
    @RequestMapping(path = "/findfriends/{userEmail:.+}")
    public String GetUserFriends(@PathVariable String userEmail) throws JsonProcessingException {
        List<Friend> friends = friendRepo.findAll();
        List<User> users = userRepo.findAll();

        List<String> friendEmails = MakeFriendEmailList(userEmail, friends);

        List<User> filteredUsers = users.stream().filter(x -> friendEmails.contains(x.GetEmail())).collect(Collectors.toList());

        return new Gson().toJson(filteredUsers);
    }

    @RequestMapping(path = "/findnonfriends/{userEmail:.+}")
    public String GetNonFriends(@PathVariable String userEmail) throws JsonProcessingException {
        List<Friend> friends = friendRepo.findAll();
        List<User> users = userRepo.findAll();

        List<String> friendEmails = MakeFriendEmailList(userEmail, friends);
        friendEmails.add(userEmail);
        List<User> filteredUsers = users.stream().filter(x -> !friendEmails.contains(x.GetEmail())).collect(Collectors.toList());

        return new Gson().toJson(filteredUsers);
    }

    @RequestMapping(path = "/adduser", method = RequestMethod.POST)
    public void AddUser(@RequestBody String user){
        User u = new Gson().fromJson(user, User.class);
        userRepo.save(u);
    }

    @RequestMapping(path = "/addfriend", method = RequestMethod.POST)
    public void AddFriend(@RequestBody String friend){
        Friend f = new Gson().fromJson(friend, Friend.class);
        friendRepo.save(f);
    }

    //TODO some tests for this function
    public List<String> MakeFriendEmailList(String userEmail, List<Friend> friends){
        //Gets all friends of the specified user
        List<Friend> filteredFriends = friends.stream().filter(x -> x.GetUser().equals(userEmail)).collect(Collectors.toList());

        //Adds all the emails of friends of the specified user to a list
        List<String> friendEmails = new ArrayList<>();
        for (Friend f : filteredFriends) {
            friendEmails.add(f.GetFriend());
        }

        return friendEmails;
    }
}
