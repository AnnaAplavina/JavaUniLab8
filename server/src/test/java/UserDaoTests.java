import data.database.DaoInitializationException;
import data.database.QueryExecutionException;
import data.database.users.User;
import data.database.users.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class UserDaoTests {


    @Test
    public void simpleTest(){
        try {
            UserDao dao = new UserDao("jdbc:postgresql://localhost:5432/studs",
                    "s264432", "ajf870", "users_test1");
            User user1 = new User("User1", "pass1");
            User user2 = new User("User2", "pass2");
            List<User> expectedUsers = new ArrayList<>();
            expectedUsers.add(user1);
            expectedUsers.add(user2);
            dao.addUser(user1);
            dao.addUser(user2);
            assertEquals(expectedUsers, dao.getAllUsers());
        } catch (DaoInitializationException | QueryExecutionException e) {
            fail(e.getMessage());
        }
    }
}
