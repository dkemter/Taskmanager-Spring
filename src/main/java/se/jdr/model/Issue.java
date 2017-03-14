package se.jdr.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue extends AbstractEntity {

	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private boolean openIssue;
	@ManyToOne
	private WorkItem workItem;

	protected Issue() {
	}

	public Issue(WorkItem workitem, String description) {
		this.description = description;
		this.openIssue = true;
		this.workItem = workitem;
	}

	public String getDescription() {
		return description;
	}

	public boolean isOpenIssue() {
		return openIssue;
	}

	public WorkItem getWorkitem() {
		return workItem;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOpenIssue(boolean openIssue) {
		this.openIssue = openIssue;
	}

	@Override
	public String toString() {
		return "Issue " + getId() + ", description: " + description + ", is open: " + openIssue + ", workitem id: "
				+ workItem;
	}

}
