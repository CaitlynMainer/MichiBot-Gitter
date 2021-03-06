package pcl.lc.irc.hooks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import pcl.lc.irc.Config;
import pcl.lc.irc.IRCBot;
import pcl.lc.utils.CommentedProperties;


/**
 * @author Caitlyn
 *
 */
@SuppressWarnings("rawtypes")
public class Announcements extends ListenerAdapter implements Runnable {

	public static Builder config = new Configuration.Builder();
	public static CommentedProperties prop = new CommentedProperties();
	public static HashMap<String, List<Object>> Announcements = new HashMap<String, List<Object>>();
	XStream xstream = new XStream(new DomDriver());
	
	public static void saveProps() {
		FileOutputStream output = null;
		try {
			output = new FileOutputStream("announcements.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.store(output, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setConfig() {
		InputStream input = null;

		try {
			
	        File file = new File("announcements.xml");
	        if (!file.exists()) {
	        	System.out.println("Creating announcements.xml");
	        	file.createNewFile();
	        }
			
			input = new FileInputStream(file);
			// load a properties file
			prop.load(input);
			Announcements.clear();
			for(String key : prop.stringPropertyNames()) {
				  List<Object> eventData = new ArrayList<Object>();
				  eventData.add("Channel");
				  eventData.add("Event");
				  eventData.add("Message");
				  Announcements.put(key, eventData);
				}
			IRCBot.log.fine(Announcements.toString());
			System.out.println(Announcements.toString());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Announcements() {
		IRCBot.registerCommand("addannounce");
		IRCBot.registerCommand("listannounce");
		IRCBot.registerCommand("removeannounce");
		IRCBot.registerCommand("reloadannounce");
		setConfig();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void onMessage(final MessageEvent event) throws Exception {
		super.onMessage(event);
		String prefix = Config.commandprefix;
		String ourinput = event.getMessage().toLowerCase();
		String trigger = ourinput.trim();
		if (trigger.length() > 1) {
			String[] firstWord = StringUtils.split(trigger);
			String triggerWord = firstWord[0];
			if (triggerWord.equals(prefix + "addannounce")) {
				
			} else if (triggerWord.equals(prefix + "listannounce")) {
				
			} else if (triggerWord.equals(prefix + "removeannounce")) {
				
			} else if (triggerWord.equals(prefix + "reloadannounce")) {
				setConfig();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
