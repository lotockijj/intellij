package book.test;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * Created by Роман Лотоцький on 24.07.2017.
 */
public class UserServiceImplTest {
    User user = Mockito.mock(User.class);
    UserDAO userDAO = Mockito.mock(UserDAO.class);
    SecurityService securityService = Mockito.mock(SecurityService.class);

    @Test
    public void testAssignPassword() throws Exception {
        UserServiceImpl userService = new UserServiceImpl(userDAO, securityService);
        userService.assignPassword(user);
        verify(securityService).md5(user.getPassword());
        verify(userDAO).updateUser(user);
    }

    @Test(expected = Exception.class)
    public void testAssignPasswordForException() throws Exception {
        UserServiceImpl userService = new UserServiceImpl(userDAO, securityService);
        Mockito.doThrow(new Exception()).when(userService).assignPassword(user);
    }
}
