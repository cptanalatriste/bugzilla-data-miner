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

	protected static final Object RESOLVED_STATUS = "RESOLVED";
	protected static final Object ASSIGNED_STATUS = "ASSIGNED";

	protected static final Object STATUS_FIELD = "status";
	protected static final Object ASSIGNEE_FIELD = "assigned_to";

	private BugReport bugReport;

	public HistoryExplorer(BugReport bugReport) {
		this.bugReport = bugReport;
	}

	public void applyBugHistory(BugHistory[] bugHistory) {
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
