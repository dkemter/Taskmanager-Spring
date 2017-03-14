package se.jdr.service;

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
import se.jdr.model.Team;
import se.jdr.model.User;
import se.jdr.repository.TeamRepository;
import se.jdr.repository.UserRepository;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class TeamServiceTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	@Autowired
	TeamService teamService;
	@MockBean
	UserRepository userRepository;
	@MockBean
	TeamRepository teamRepository;


	User user = new User("Robertasdasdasd", "roberts", "Hello", "world");
	Team team = new Team("Team");


	@Test
	public void shouldThrowExceptionWhenTeamIsFull() throws ServiceException {
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Team is full!");
		when(teamRepository.save(team)).thenReturn(team);
		when(userRepository.countByTeamId(team.getId())).thenReturn(12L);
		teamService.addUserToTeam(user, team);
	}

}
