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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class LogicApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private User user = new User("test@test.com", "testuser", "http://");
    private Friend friend = new Friend("test@test.com", "testfriend@test.com");

    @Autowired
    FriendLogic friendLogic;
    @Autowired
    UserLogic userLogic;
    @Autowired
    LoggerLogic loggerLogic;

    Logger logger = (Logger) LoggerFactory.getLogger(LoggerLogic.class);

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FriendLogic getNonFriends tests">
    @Test
    public void getNonFriendsCorrectly(){
        List<User> nonFriends = friendLogic.getNonFriends("hasnofriends@test.com");

        int expectedAmmount = 2;

        Assert.assertEquals(expectedAmmount, nonFriends.size());
    }

    @Test
    public void getNonFriendsIncorrectly(){
        List<User> nonFriends = friendLogic.getNonFriends("test");

        int expectedAmmount = 2;

        Assert.assertEquals(expectedAmmount, nonFriends.size());
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

    // <editor-fold defaultstate="collapsed" desc="FriendLogic getUserFriends tests">
    @Test
    public void getUserFriendsCorrectly(){
        List<User> friends = friendLogic.getUserFriends("testfriend@test.com");

        int expectedAmmount = 0;

        Assert.assertEquals(expectedAmmount, friends.size());
    }

    @Test
    public void getUserFriendsIncorrectly(){
        List<User> friends = friendLogic.getUserFriends("test");

        int expectedAmmount = 0;

        Assert.assertEquals(expectedAmmount, friends.size());
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
