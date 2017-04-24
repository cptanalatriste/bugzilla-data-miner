package ucl.crest.bugzilla.miner;

import javax.ws.rs.core.UriBuilder;

public class Configuration {
	
	private String home = "https://bugs.documentfoundation.org/rest/bug";
	private String  product = "LibreOffice";
	
	public String getAllBugsResource(int limit) {
		return UriBuilder.fromUri(this.home).queryParam("product", product).queryParam("limit", limit).build().toString();
	}

}