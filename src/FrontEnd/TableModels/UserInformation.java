package FrontEnd.TableModels;

import Database.User;

public class UserInformation {
    public  static User user;

    public UserInformation(User user1){
        user = user1;
    }

    public static User getUserInformation(){
        return user;
    }
}
