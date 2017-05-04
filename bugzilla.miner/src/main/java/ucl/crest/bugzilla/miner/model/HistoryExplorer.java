package ucl.crest.bugzilla.miner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;

public class HistoryExplorer {

	protected static final String RESOLVED_STATUS = "RESOLVED";
	protected static final String ASSIGNED_STATUS = "ASSIGNED";

	protected static final String STATUS_FIELD = "status";
	protected static final String PRIORITY_FIELD = "priority";
	protected static final String ASSIGNEE_FIELD = "assigned_to";

	private BugReport bugReport;

	public HistoryExplorer(BugReport bugReport) {
		this.bugReport = bugReport;
	}

	public <T> void applyBugHistory(BugHistory[] bugHistory) {
		Predicate<BugHistory> resolvedPredicate = getResolvedPredicate();

		List<BugHistory> historyList = Arrays.asList(bugHistory);
		BugHistory resolvedHistory = IterableUtils.find(historyList, resolvedPredicate);

		if (resolvedHistory != null) {
			bugReport.setResolvedBy(resolvedHistory.getWho());
			bugReport.setResolutionDate(resolvedHistory.getWhen());

			Predicate<BugHistory> resolverAssignedPredicate = this.getResolverAssignedPredicate();
			BugHistory resolverAssignedHistory = IterableUtils.find(historyList, resolverAssignedPredicate);

			if (resolverAssignedHistory != null) {
				bugReport.setResolverAssigned(resolverAssignedHistory.getWhen());

			}

			bugReport.setResolutionStart(getResolutionStart(historyList));
		}

		// Test priority changes with Bug 86675
		this.setPriorityChangeInformation(historyList);

	}

	private void setPriorityChangeInformation(List<BugHistory> historyList) {
		Predicate<BugHistory> priorityChangePredicate = getPriorityChangePredicate();
		Iterable<BugHistory> priorityChanges = IterableUtils.filteredIterable(historyList, priorityChangePredicate);

		List<BugHistory> priorityChangesList = new ArrayList<>();
		priorityChanges.forEach(priorityChangesList::add);

		Collections.sort(priorityChangesList, new Comparator<BugHistory>() {

			@Override
			public int compare(BugHistory o1, BugHistory o2) {
				return o1.getWhen().compareTo(o2.getWhen());
			}
		});

		if (priorityChangesList.size() > 0) {
			BugHistory firstPriorityChange = priorityChangesList.get(0);
			this.bugReport.setPriorityChanger(firstPriorityChange.getWho());
			this.bugReport.setPriorityChange(firstPriorityChange.getWhen());

			ChangeItem priorityValues = IterableUtils.find(Arrays.asList(firstPriorityChange.getChanges()),
					new Predicate<ChangeItem>() {

						@Override
						public boolean evaluate(ChangeItem changeItem) {
							return PRIORITY_FIELD.equals(changeItem.getField().toString());
						}
					});

			this.bugReport.setOriginalPriority(new Priority(priorityValues.getRemoved()));
			this.bugReport.setNewPriority(new Priority(priorityValues.getAdded()));
		}

	}

	private Predicate<BugHistory> getPriorityChangePredicate() {

		return new Predicate<BugHistory>() {

			@Override
			public boolean evaluate(BugHistory bugHistory) {
				for (ChangeItem changeItem : bugHistory.getChanges()) {
					if (PRIORITY_FIELD.equals(changeItem.getField().toString())) {
						return true;
					}
				}

				return false;
			}
		};
	}

	private Date getResolutionStart(List<BugHistory> historyList) {
		Predicate<BugHistory> resolverInteractionsPred = getResolverInteractionsPred();
		Iterable<BugHistory> resolverInteractions = IterableUtils.filteredIterable(historyList,
				resolverInteractionsPred);
		List<BugHistory> resolverInteractionsList = new ArrayList<BugHistory>();

		resolverInteractions.forEach(resolverInteractionsList::add);
		Collections.sort(resolverInteractionsList, new Comparator<BugHistory>() {

			@Override
			public int compare(BugHistory o1, BugHistory o2) {
				return o1.getWhen().compareTo(o2.getWhen());
			}

		});

		return resolverInteractionsList.get(0).getWhen();
	}

	private Predicate<BugHistory> getResolverInteractionsPred() {
		return new Predicate<BugHistory>() {

			final String resolver = bugReport.getResolvedBy().toString();

			public boolean evaluate(BugHistory bugHistory) {

				if (resolver.equals(bugHistory.getWho().toString())) {
					return true;
				}

				return false;
			}
		};
	}

	private static Predicate<BugHistory> getResolvedPredicate() {
		return new Predicate<BugHistory>() {

			public boolean evaluate(BugHistory bugHistory) {
				for (ChangeItem changeItem : bugHistory.getChanges()) {
					if (STATUS_FIELD.equals(changeItem.getField().toString())
							&& RESOLVED_STATUS.equals(changeItem.getAdded())) {
						return true;
					}
				}
				return false;
			}
		};
	}

	private Predicate<BugHistory> getResolverAssignedPredicate() {
		final String resolver = bugReport.getResolvedBy().toString();

		return new Predicate<BugHistory>() {

			public boolean evaluate(BugHistory bugHistory) {
				for (ChangeItem changeItem : bugHistory.getChanges()) {
					if (ASSIGNEE_FIELD.equals(changeItem.getField().toString())
							&& resolver.equals(changeItem.getAdded())) {
						return true;
					}
				}

				return false;
			}
		};
	}
}
