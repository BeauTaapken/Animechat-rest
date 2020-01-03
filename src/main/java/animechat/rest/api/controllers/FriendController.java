package animechat.rest.api.controllers;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import animechat.rest.api.repository.FriendRepository;
import animechat.rest.api.repository.UserRepository;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/friend")
@RestController
@Api(value="AnimeChat")
public class FriendController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FriendRepository friendRepo;

    //Function for getting users friends based on userEmail
    @ApiOperation(value = "Get a list of all friends of a user", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "/findfriends/{userEmail:.+}")
    public String getUserFriends(@PathVariable String userEmail) {
        List<Friend> friends = friendRepo.findAll();
        List<User> users = userRepo.findAll();

        List<String> friendEmails = makeFriendEmailList(userEmail, friends);

        List<User> filteredUsers = users.stream().filter(x -> friendEmails.contains(x.getEmail())).collect(Collectors.toList());

        return new Gson().toJson(filteredUsers);
    }

    @ApiOperation(value = "Get a list of all non friends of a user", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "/findnonfriends/{userEmail:.+}")
    public String getNonFriends(@PathVariable String userEmail) {
        List<Friend> friends = friendRepo.findAll();
        List<User> users = userRepo.findAll();

        List<String> friendEmails = makeFriendEmailList(userEmail, friends);
        friendEmails.add(userEmail);
        List<User> filteredUsers = users.stream().filter(x -> !friendEmails.contains(x.getEmail())).collect(Collectors.toList());

        return new Gson().toJson(filteredUsers);
    }

    @ApiOperation(value = "Adds a user friend combination to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added user friend combination"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(path = "/addfriend")
    public void addFriend(@RequestBody String friend){
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
