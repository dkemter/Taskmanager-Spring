package se.jdr.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import se.jdr.config.TestConfig;
import se.jdr.model.User;
import se.jdr.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
	UserService userService;

	@MockBean
	UserRepository userRepository;
	@Before



	@Test
	public void shouldThrowExceptionWhenUsernameTooShort() throws ServiceException {
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Username is too short!");
		User user = new User("cl1", "Robert", "Savela", "12");
		userService.addOrUpdateUser(user);
	}

}
