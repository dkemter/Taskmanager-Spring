package se.jdr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.IssueRepository;
import se.jdr.repository.WorkItemRepository;

@Component
public final class IssueService {

	private final IssueRepository issuerepository;
	private final WorkItemRepository workItemRepository;
	private final ServiceTransaction transaction;

	@Autowired
	public IssueService(IssueRepository issueRepository, WorkItemRepository workItemRepository,
			ServiceTransaction transaction) {
		this.issuerepository = issueRepository;
		this.workItemRepository = workItemRepository;
		this.transaction = transaction;
	}

	public Issue addIssue(WorkItem workItem, String description) throws ServiceException {
		if (workItem.getStatus() == Status.DONE) {
			return transaction.execute(() -> {
				workItem.setStatus(Status.UNSTARTED);
				workItemRepository.save(workItem);
				return addOrUpdate(new Issue(workItem, description));
			});
		} else {
 			throw new ServiceException("Invalid work item status");
		}
	}

	public Issue addOrUpdate(Issue issue) {
		return issuerepository.save(issue);
	}

	public Issue updateDescription(Issue issue, String description) {
		issue.setDescription(description);
		return addOrUpdate(issue);
	}

	public Issue updateStatus(Issue issue, boolean status) {
		issue.setOpenIssue(status);
		return addOrUpdate(issue);
	}
}
