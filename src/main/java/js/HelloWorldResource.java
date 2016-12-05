package js;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import assign.services.EavesdropService;

@Path("/path")
public class HelloWorldResource {
	
	EavesdropService eavesdropservice;
	
	public HelloWorldResource() {
		this.eavesdropservice = new EavesdropService();
	}
	
	@GET
	@Path("/solum")
	@Produces("text/html")
	public String getAllProjects() throws Exception {
		ArrayList<String> parsed = this.eavesdropservice.getYears();
//		Set<String> keys = parsed.keySet();
		
		HashMap<String, Integer> meetings = this.eavesdropservice.getMeetings(parsed);
		Set<String> keys = meetings.keySet();
		String str = "";
		for(String item : keys){
			str += item + "~" + meetings.get(item) + "~";
		}
		
		return str;
	}
}
