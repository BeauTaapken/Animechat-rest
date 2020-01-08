package animechat.rest.api.controller;

import animechat.rest.api.logic.LoggerLogic;
import animechat.rest.api.logic.UserLogic;
import animechat.rest.api.model.User;
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
    private UserLogic userLogic;

    @Autowired
    private LoggerLogic loggerLogic;

    @ApiOperation(value = "Adds a user to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(path = "/adduser")
    public void addUser(@RequestBody User user){
        try{
            userLogic.addUser(user);
        }
        catch(Exception e){
            loggerLogic.errorLogging(String.valueOf(e));
        }
    }
}
