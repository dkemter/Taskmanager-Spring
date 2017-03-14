package se.jdr.model;

import org.springframework.data.annotation.CreatedBy;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "workitems")
public class WorkItem extends AbstractEntity {

	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ManyToOne
	private User user;
	private String dateOfCompletion;
	@OneToMany(mappedBy = "workItem")
	@CreatedBy
	private Collection<Issue> issues;

	protected WorkItem() {
	}

	public WorkItem(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = Status.STARTED;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public User getUser() {
		return user;
	}

	public String getDateOfCompletion() {
		return dateOfCompletion;
	}

	public Collection<Issue> getIssues() {
		return issues;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setIssues(Collection<Issue> issues) {
		this.issues = issues;
	}

	public void setDateOfCompletion(String dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status {
		DONE, UNSTARTED, STARTED, ARCHIVED
	}

	@Override
	public String toString() {
		return "Workitem " + getId() + ", title: " + title + ", description: " + description + ", status: " + status
				+ ", assignedUserId: " + user + ", dateOfCompletion: " + dateOfCompletion;
	}

}
