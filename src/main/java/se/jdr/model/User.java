package se.jdr.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity

@Table(name = "users")
public class User extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false, unique = true)
	private String userId;
	@Column(nullable = false)
	private boolean activeUser;
	@ManyToOne
	private Team team;
	@OneToMany(mappedBy = "user")
	private Collection<WorkItem> workitems;

	protected User() {
	}

	public User(String username, String firstname, String lastname, String userId) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.activeUser = true;
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isActiveUser() {
		return activeUser;
	}

	public Team getTeam() {
		return team;
	}

	public String getUserId() {
		return userId;
	}

	public void setActiveUser(boolean activeUser) {
		this.activeUser = activeUser;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "User " + getId() + ", username: " + username + ", firstname: " + firstname + ", lastname: " + lastname
				+ ", userId : " + userId + ", active: " + activeUser + ", teamId: " + team;
	}

}
