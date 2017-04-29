package ucl.crest.bugzilla.miner;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

public class Configuration {

	private String home = "https://bugs.documentfoundation.org/rest/bug";
	private String historyFragment = "history";
	private String product = "LibreOffice";

	/***
	 * The components taken from
	 * https://bugs.documentfoundation.org//describecomponents.cgi?product=LibreOffice
	 * 
	 * And names are taken from here:
	 * https://bugs.documentfoundation.org/rest/product/LibreOffice
	 * 
	 * @return
	 */
	public List<String> getComponentCatalog() {
		return Arrays.asList(new String[] { "Calc", "Chart" });
	}

	public String getAllBugsResource(int limit, String component) {
		UriBuilder uriBuilder = UriBuilder.fromUri(this.home).queryParam("product", product);

		if (limit > 0) {
			uriBuilder = uriBuilder.queryParam("limit", limit);
		}

		if (component != null) {
			uriBuilder = uriBuilder.queryParam("component", component);
		}

		return uriBuilder.build().toString();
	}

	public String getBugHistoryResource(String issueKey) {
		return UriBuilder.fromUri(this.home).path(issueKey).path(historyFragment).build().toString();
	}

}
