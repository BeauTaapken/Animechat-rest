package animechat.rest.api.interfac;

import animechat.rest.api.model.Friend;
import animechat.rest.api.model.User;

import java.util.List;

public interface IFriend {
    String getUserFriends(String userEmail);

    String getNonFriends(String userEmail);

    void addFriend(Friend friend);
}
