package animechat.rest.api;

import animechat.rest.api.logic.FriendLogic;
import animechat.rest.api.logic.LoggerLogic;
import animechat.rest.api.logic.UserLogic;
import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class IntergrationApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private User user = new User("test@test.com", "testuser", "http://");
    private User userFriend = new User("testfriend@test.com", "testfriend", "http://");
    private Friend friend = new Friend("test@test.com", "testfriend@test.com");

    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    private final Logger logger = (Logger) LoggerFactory.getLogger(LoggerLogic.class);

    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserLogic userLogic;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        userLogic.addUser(user);

        userLogic.addUser(userFriend);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Error code tests">
    @Test
    public void get404Error() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/wrongurl")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));

        MvcResult result = action.andReturn();

        int expected = 404;

        Assert.assertEquals(expected, result.getResponse().getStatus());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="/friend/findnonfriends tests">
    @Test
    public void getFilledNonFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findnonfriends/testfriend@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[{\"email\":\"test@test.com\",\"name\":\"testuser\",\"imgUrl\":\"http://\"}]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void getFilledNonFriendListNoAtEmail() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findnonfriends/testfriend.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void getFilledNonFriendListNoDotEmail() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findnonfriends/testfriend@test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void getEmptyNonFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findnonfriends/test@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="/user/adduser tests">
    @Test
    public void addUserCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void addUserButFriend() throws Exception {
        listAppender.start();
        logger.addAppender(listAppender);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(friend))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        List<ILoggingEvent> logsList = listAppender.list;

        Level expectedResult = Level.ERROR;

        Assert.assertEquals(expectedResult, logsList.get(logsList.size() - 1).getLevel());
    }

    @Test
    public void addUserIncorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="/friend/addfriend tests">
    @Test
    public void addFriendCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/addfriend")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(friend))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void addFriendButUser() throws Exception {
        listAppender.start();
        logger.addAppender(listAppender);

        mockMvc.perform(MockMvcRequestBuilders.post("/friend/addfriend")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        List<ILoggingEvent> logsList = listAppender.list;

        Level expectedResult = Level.ERROR;

        Assert.assertEquals(expectedResult, logsList.get(logsList.size() - 1).getLevel());
    }

    @Test
    public void addFriendIncorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/addfriend")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="/friend/findfriends tests">
    @Test
    public void getFilledFriendList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/addfriend")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(friend))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findfriends/test@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[{\"email\":\"testfriend@test.com\",\"name\":\"testfriend\",\"imgUrl\":\"http://\"}]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void getFilledFriendListNoAtEmail() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findfriends/test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void getFilledFriendListNoDotEmail() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findfriends/test@test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
    @Test
    public void getEmptyFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findfriends/hasnofriends@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();

        String expectedResult = "[]";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
    // </editor-fold>
}
