import java.util.List;

public class UserDatabase {
    private static List<User> userList;
    private static final String fileName = "database.ser";

    public static void load() {

    }

    public static void addToUserList(User user) {
        userList.add(user);
    }

    public static void save() {

    }

    public static User getUser(String username, String password) {
        return null;
    }
}
