package animechat.rest.api;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;
import org.junit.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class ModelApplicationTests {
    private User user = new User("test@test.com", "testuser", "http://");
    private Friend friend = new Friend(1, "test@test.com", "testfriend@test.com");


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


}
