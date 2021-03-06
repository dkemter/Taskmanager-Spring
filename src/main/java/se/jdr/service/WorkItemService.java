package se.jdr.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.Issue;
import se.jdr.model.User;
import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;
import se.jdr.repository.IssueRepository;
import se.jdr.repository.WorkItemRepository;

@Component
public final class WorkItemService {

	private final WorkItemRepository workItemRepository;
	private final UserService userService;
	private final IssueRepository issueRepository;
	private final ServiceTransaction transaction;

	@Autowired
	public WorkItemService(WorkItemRepository workItemRepository, UserService userService,
			IssueRepository issueRepository, ServiceTransaction transaction) {
		this.workItemRepository = workItemRepository;
		this.userService = userService;
		this.issueRepository = issueRepository;
		this.transaction = transaction;
	}

	public WorkItem addOrUpdateWorkItem(WorkItem workItem) {
		return workItemRepository.save(workItem);
	}

	public WorkItem updateWorkItemStatus(WorkItem workItem, WorkItem.Status status) throws ServiceException {
		if (status == Status.ARCHIVED) {
			return transaction.execute(() -> {
				Collection<Issue> issues = issueRepository.findByWorkItemId(workItem.getId());
				issues.forEach(i -> i.setOpenIssue(false));

				issueRepository.save(issues);
				workItem.setStatus(status);

				return workItemRepository.save(workItem);
			});

		} else {
			workItem.setStatus(status);
			return addOrUpdateWorkItem(workItem);
		}
	}

	public void addUserToWorkItem(WorkItem workItem, User user) throws ServiceException {
		User userToDB = userService.addOrUpdateUser(user);
		if (userToDB.isActiveUser() && isValidAmountOfWorkItems(userToDB)) {
			workItem.setUser(userToDB);
			addOrUpdateWorkItem(workItem);
		} else {
			throw new ServiceException("user must be active and cannot have more than 5 work items");
		}
	}

	public Collection<WorkItem> getWorkItemsByStatus(WorkItem.Status status) {
		return workItemRepository.findByStatus(status);
	}

	public Collection<WorkItem> getAllWorkItemsByTeam(Long teamId) {
		return workItemRepository.getWorkItemsByTeamId(teamId);
	}

	public Collection<WorkItem> getAllWorkItemsByUser(User user) {
		return workItemRepository.findByUserId(user.getId());
	}

	public Collection<WorkItem> getAllWorkItemsWithIssues() {
		return workItemRepository.getAllWorkItemsWithIssues();
	}

	public Collection<WorkItem> getWorkItemByDescripton(String description) {
		return workItemRepository.findByDescription(description);
	}

	private boolean isValidAmountOfWorkItems(User user) {
		return workItemRepository.countByUserId(user.getId()) < 5;
	}

}
