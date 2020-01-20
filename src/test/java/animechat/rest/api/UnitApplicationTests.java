package animechat.rest.api;

import animechat.rest.api.logic.FriendLogic;
import animechat.rest.api.logic.LoggerLogic;
import animechat.rest.api.logic.UserLogic;
import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class UnitApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private User user = new User("test@test.com", "testuser", "http://");
    private Friend friend = new Friend("test@test.com", "testfriend@test.com");

    @Autowired
    private FriendLogic friendLogic;
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private LoggerLogic loggerLogic;

    private final Gson gson = new Gson();

    private final Logger logger = (Logger) LoggerFactory.getLogger(LoggerLogic.class);

    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FriendLogic getNonFriends tests">
    @Test
    public void getNonFriendsCorrectly(){
        String nonFriends = friendLogic.getNonFriends("testfriend@test.com");

        String expectedAmmount = "[{\"email\":\"test@test.com\",\"name\":\"testuser\",\"imgUrl\":\"http://\"}]";

        Assert.assertEquals(expectedAmmount, nonFriends);
    }

    @Test
    public void getNonFriendsIncorrectly(){
        String nonFriends = friendLogic.getNonFriends("test");

        String expectedAmmount = "[]";

        Assert.assertEquals(expectedAmmount, nonFriends);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FriendLogic addFriend tests">
    @Test
    public void addFriendCorrectly(){
        friendLogic.addFriend(friend);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserLogic addUser tests">
    @Test
    public void addUserCorrectly(){
        userLogic.addUser(user);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserLogic getAllUsers tests">
    @Test
    public void getAllUsers()
    {
        userLogic.addUser(user);
        userLogic.addUser(new User("testfriend@test.com", "testfriend", "http://"));

        List<User> users = userLogic.getAllUsers();

        Assert.assertEquals(2, users.size());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FriendLogic getUserFriends tests">
    @Test
    public void getUserFriendsCorrectly(){
        String friends = friendLogic.getUserFriends("testfriend@test.com");

        String expectedAmmount = "[]";

        Assert.assertEquals(expectedAmmount, friends);
    }

    @Test
    public void getUserFriendsIncorrectly(){
        String friends = friendLogic.getUserFriends("test");

        String expectedAmmount = "[]";

        Assert.assertEquals(expectedAmmount, friends);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isEmail tests">
    @Test
    public void CorrectEmail(){
        boolean isCorrectEmail = friendLogic.isEmail("test@test.com");
        Assert.assertEquals(true, isCorrectEmail);
    }

    @Test
    public void IncorrectEmailNoAt(){
        boolean isCorrectEmail = friendLogic.isEmail("test.com");
        Assert.assertEquals(false, isCorrectEmail);
    }

    @Test
    public void IncorrectEmailNoDot(){
        boolean isCorrectEmail = friendLogic.isEmail("test@test");
        Assert.assertEquals(false, isCorrectEmail);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Logger tests">
    @Test
    public void AddFriendButUser() throws Exception {
        listAppender.start();
        logger.addAppender(listAppender);

        loggerLogic.errorLogging("this is a test");

        List<ILoggingEvent> logsList = listAppender.list;
        Assert.assertEquals("this is a test", logsList.get(logsList.size() - 1).getMessage());
        Assert.assertEquals(Level.ERROR, logsList.get(logsList.size() - 1).getLevel());
    }
    // </editor-fold>
}
