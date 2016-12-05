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

@Path("/path")
public class HelloWorldResource {
	

	
	public HelloWorldResource() {

	}
	
	@GET
	@Path("/solum")
	@Produces("text/html")
	public String getAllProjects() throws Exception {
		ArrayList<String> parsed = getYears();
//		Set<String> keys = parsed.keySet();
		
		HashMap<String, Integer> meetings = getMeetings(parsed);
		Set<String> keys = meetings.keySet();
		String str = "";
		for(String item : keys){
			str += item + "~" + meetings.get(item) + "~";
		}
		
		return str;
	}

	public ArrayList<String> getYears() throws IOException{
		ArrayList<String> res = new ArrayList<String>();
		Document doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/solum_team_meeting/").get();
		Elements links = doc.select("body a");
	    if (links != null) {
		    ListIterator<Element> iter = links.listIterator();		    	
		    while(iter.hasNext()) {
	    		Element e = (Element) iter.next();
	    		String s = e.html();
	    		if ( s != null && s.matches("^\\d{4}/")) {
	    			res.add(s);
	    		}
		    }
	    }
		return res;
	}
	
	public HashMap<String, Integer> getMeetings(ArrayList <String> years) throws IOException{
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		for(String item : years){
			HashMap<String, Integer> num = new HashMap<String, Integer>();
			Integer count = 0;
			Document doc = Jsoup.connect("http://eavesdrop.openstack.org/meetings/solum_team_meeting/" + item).get();
			Elements links = doc.select("body a");
		    if (links != null) {
			    ListIterator<Element> iter = links.listIterator();		    	
			    while(iter.hasNext()) {
		    		Element e = (Element) iter.next();
		    		String s = e.html();
		    		if ( s != null && s.contains("solum_team_meeting")) {
//		    			System.out.println(s);
		    			String split[] = s.split("\\.");
		    			String secsplit[] = split[1].split("-");
		    			String r = split[0] + secsplit[0] + secsplit[1] + secsplit[2];
		    			num.put(r, res.get(s));
		    		}
			    }
		    }
		    if(item.equals("2014/")){
		    	Set<String> keys = num.keySet();
		    	for(String yo : keys){
//		    		System.out.println(yo);
		    	}
		    }
		    res.put(item, num.size());
		}
		return res;
	}
}
