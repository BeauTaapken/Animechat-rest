package animechat.rest.api.controller;

import animechat.rest.api.interfac.IFriend;
import animechat.rest.api.logic.FriendLogic;
import animechat.rest.api.logic.LoggerLogic;
import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/friend")
@RestController
@Api(value="AnimeChat")
public class FriendController implements IFriend {
    @Autowired
    private FriendLogic friendLogic;

    private final Gson gson = new Gson();

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
//        List<User> filteredUsers = new ArrayList<>();
        String filteredUsers = gson.toJson("");

        if(friendLogic.isEmail(userEmail)){
            filteredUsers = friendLogic.getUserFriends(userEmail);
        }

        return filteredUsers;
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
//        List<User> filteredUsers = new ArrayList<>();
        String filteredUsers = "";

        if(friendLogic.isEmail(userEmail)){
            filteredUsers = friendLogic.getNonFriends(userEmail);
        }

        return filteredUsers;
    }

    @ApiOperation(value = "Adds a user friend combination to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added user friend combination"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(path = "/addfriend")
    public void addFriend(@RequestBody Friend friend){
        try{
            friendLogic.addFriend(friend);
        }
        catch(Exception e){
            LoggerLogic.errorLogging(String.valueOf(e));
        }
    }
}
