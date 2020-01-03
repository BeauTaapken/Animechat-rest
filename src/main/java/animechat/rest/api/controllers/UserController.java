package animechat.rest.api.controllers;

import animechat.rest.api.model.User;
import animechat.rest.api.repository.UserRepository;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Api(value="AnimeChat")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @ApiOperation(value = "Adds a user to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(path = "/adduser")
    public void addUser(@RequestBody String user){
        User u = new Gson().fromJson(user, User.class);
        userRepo.save(u);
    }
}
