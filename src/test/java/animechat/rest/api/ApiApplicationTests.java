package animechat.rest.api;

import animechat.rest.api.controllers.FriendController;
import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import com.google.gson.Gson;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class ApiApplicationTests {
    private FriendController fc = new FriendController();
    private User user = new User("test@test.com", "testuser", "http://");
    private Friend friend = new Friend(1, "test@test.com", "testfriend@test.com");
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Code for testing the User model GetEmail
     */
    @Test
    public void GetUserEmailCorrect(){
        Assert.assertEquals("test@test.com", user.getEmail());
    }


    @Test
    public void GetUserEmailWrong(){
        Assert.assertNotEquals("wrongTest@test.com", user.getEmail());
    }

    /**
     * Code for testing the User model SetEmail
     */
    @Test
    public void SetUserEmailCorrect(){
        user.setEmail("newtest@test.com");
        Assert.assertEquals("newtest@test.com", user.getEmail());
    }

    @Test
    public void SetUserEmailWrong(){
        user.setEmail("newwrongtest@test.com");
        Assert.assertNotEquals("newtest@test.com", user.getEmail());
    }

    /**
     * Code for testing the User model GetName
     */
    @Test
    public void GetUserNameCorrect(){
        Assert.assertEquals("testuser", user.getName());
    }

    @Test
    public void GetUserNameWrong(){
        Assert.assertNotEquals("testwronguser", user.getName());
    }

    /**
     * Code for testing the User model SetNAme
     */
    @Test
    public void SetUserNameCorrect(){
        user.setName("newtestuser");
        Assert.assertEquals("newtestuser", user.getName());
    }

    @Test
    public void SetUserNameWrong(){
        user.setName("newwrongtestuser");
        Assert.assertNotEquals("newtestuser", user.getName());
    }

    /**
     * Code for testing the User model GetImgUrl
     */
    @Test
    public void GetImgUrlCorrect(){
        Assert.assertEquals("http://", user.getImgUrl());
    }

    @Test
    public void GetImgUrlWrong(){
        Assert.assertNotEquals("wronghttp://", user.getImgUrl());
    }

    /**
     * Code for testing the User model setImgUrl
     */
    @Test
    public void SetImgUrlCorrect(){
        user.setImgUrl("newhttp://");
        Assert.assertEquals("newhttp://", user.getImgUrl());
    }

    @Test
    public void SetImgUrlWrong(){
        user.setImgUrl("newwronghttp://");
        Assert.assertNotEquals("newhttp://", user.getImgUrl());
    }

    /**
     * Code for testing the Friend model GetId
     */
    @Test
    public void GetFriendIdCorrect(){
        Assert.assertEquals(1, friend.getId());
    }

    @Test
    public void GetFriendIdWrong(){
        Assert.assertNotEquals("wrong", friend.getId());
    }

    /**
     * Code for testing the Friend model SetId
     */
    @Test
    public void SetFriendIdCorrect(){
        friend.setId(8);
        Assert.assertEquals(8, friend.getId());
    }

    @Test
    public void SetFriendIdWrong(){
        friend.setId(8);
        Assert.assertNotEquals("wrong", friend.getId());
    }

    /**
     * Code for testing the Friend model GetUserEmail
     */
    @Test
    public void GetUserEmailFromFriendCorrect(){
        Assert.assertEquals("test@test.com", friend.getUserEmail());
    }

    @Test
    public void GetUserEmailFromFriendWrong(){
        Assert.assertNotEquals("testwrong@test.com", friend.getUserEmail());
    }

    /**
     * Code for testing the Friend model SetUserEmail
     */
    @Test
    public void SetUserEmailFromFriendCorrect(){
        friend.setUserEmail("newtest@test.com");
        Assert.assertEquals("newtest@test.com", friend.getUserEmail());
    }

    @Test
    public void SetUserEmailFromFriendWrong(){
        friend.setUserEmail("newwrongtest@test.com");
        Assert.assertNotEquals("newtest@test.com", friend.getUserEmail());
    }

    /**
     * Code for testing the Friend model GetFriendEmail
     */
    @Test
    public void GetFriendEmailCorrect(){ Assert.assertEquals("testfriend@test.com", friend.getFriendEmail()); }

    @Test
    public void GetFriendEmailWrong(){ Assert.assertNotEquals("testwrongfriend@test.com", friend.getFriendEmail()); }

    /**
     * Code for testing the Friend model SetFriendEmail
     */
    @Test
    public void SetFriendEmailCorrect(){
        friend.setFriendEmail("newtestfriend@test.com");
        Assert.assertEquals("newtestfriend@test.com", friend.getFriendEmail());
    }

    @Test
    public void SetFriendEmailWrong(){
        friend.setFriendEmail("newwrongtestfriend@test.com");
        Assert.assertNotEquals("newtestfriend@test.com", friend.getFriendEmail());
    }

    /**
     * Code for testing if non existing url returns 404 status
     */
    @Test
    public void Get404Error() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/wrongurl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    /**
     * Code for testing if friend/findfriends API call returns a json format with the correct friend
     */
    @Test
    public void GetFilledFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findfriends/test@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();
        Assert.assertEquals("[{\"email\":\"testfriend@test.com\",\"name\":\"testfriend\",\"imgUrl\":\"http://\"}]", result.getResponse().getContentAsString());
    }

    /**
     * Code for testing if friend/findfriends API call can return an empty list as json
     */
    @Test
    public void GetEmptyFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findfriends/testfriend@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();
        Assert.assertEquals("[]", result.getResponse().getContentAsString());
    }

    /**
     * Code for testing if friend/findfriends API call returns a json format with the correct friend
     */
    @Test
    public void GetFilledNonFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findnonfriends/testfriend@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();
        System.out.println(result.getResponse().getContentAsString());
        Assert.assertEquals("[{\"email\":\"test@test.com\",\"name\":\"testuser\",\"imgUrl\":\"http://\"}]", result.getResponse().getContentAsString());
    }

    /**
     * Code for testing if friend/findfriends API call can return an empty list as json
     */
    @Test
    public void GetEmptyNonFriendList() throws Exception {
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/friend/findnonfriends/test@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = action.andReturn();
        System.out.println(result.getResponse().getContentAsString());
        Assert.assertEquals("[]", result.getResponse().getContentAsString());
    }

    /**
     * Code for testing if a API call exists
     * @throws Exception This has to be added for the .perform function of mockMvc
     */
    @Test
    public void AddUserCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    /**
     * Code for testing if a API call handles correctly when incorrect data has been sent
     * @throws Exception This has to be added for the .perform function of mockMvc
     */
    @Test
    public void AddUserIncorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Code for testing if a API call exists
     * @throws Exception This has to be added for the .perform function of mockMvc
     */
    @Test
    public void AddFriendCorrectly() throws Exception {
        Friend content = new Friend("test@test.com", "testfriend@test.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/friend/addfriend")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(content))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }


    /**
     * Code for testing if a API call handles correctly when incorrect data has been sent
     * @throws Exception This has to be added for the .perform function of mockMvc
     */
    @Test
    public void AddFriendIncorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/friend/addfriend")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Code for testing the FriendController MakeFriendEmailList
     */
    @Test
    public void CorrectFriendEmailList(){
        List<Friend> friends = new ArrayList<>();
        friends.add(new Friend(1, "testuser@test.com", "testfriend@test.com"));
        friends.add(new Friend(2, "testuser@test.com", "testfriend2@test.com"));
        friends.add(new Friend(2, "test@test.com", "testfriend2@test.com"));
        List<String> userEmails = fc.makeFriendEmailList("testuser@test.com", friends);
        List<String> expectedUserEmails = new ArrayList<>();
        expectedUserEmails.add("testfriend@test.com");
        expectedUserEmails.add("testfriend2@test.com");
        Assert.assertEquals(expectedUserEmails, userEmails);
    }
}
