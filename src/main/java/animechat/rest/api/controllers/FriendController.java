package animechat.rest.api.controllers;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import animechat.rest.api.repository.FriendRepository;
import animechat.rest.api.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
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
    private ObjectMapper mapper;


    FriendController(){
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    //Function for getting users friends based on userEmail
    @RequestMapping(path = "/findfriends/{userEmail}")
    public String GetUserFriends(@PathVariable String userEmail) throws JsonProcessingException {
        List<Friend> friends = friendRepo.findAll();

        //Gets all friends of the specified user
        List<Friend> filteredFriends = friends.stream().filter(x -> x.GetUser().equals(userEmail)).collect(Collectors.toList());

        //Adds all the emails of friends of the specified user to a list
        List<String> friendEmails = new ArrayList<>();
        for (Friend f : filteredFriends) {
            friendEmails.add(f.GetFriend());
        }

        List<User> users = userRepo.findAll();

        //Gets all the user data of the friends
        List<User> filteredUsers = users.stream().filter(x -> friendEmails.contains(x.GetEmail())).collect(Collectors.toList());


        return new Gson().toJson(filteredUsers);
    }

    //TODO find friends user doesn't have
    @RequestMapping(path = "/findnonfriends/{userEmail}")
    public String GetNonFriends(@PathVariable String userEmail) throws JsonProcessingException {
        List<Friend> friends = friendRepo.findAll();

        for (Friend t : friends) {
            System.out.println(t.GetUser());
        }

        List<Friend> filteredFriends = friends.stream().filter(x -> x.GetUser().equals(userEmail)).collect(Collectors.toList());

        return new Gson().toJson(filteredFriends);
    }

    //TODO get search query for specific names of all people
}
