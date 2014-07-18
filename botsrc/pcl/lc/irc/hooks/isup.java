/**
 * 
 */
package pcl.lc.irc.hooks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import pcl.lc.irc.IRCBot;

/**
 * @author Caitlyn
 *
 */
@SuppressWarnings("rawtypes")
public class isup extends ListenerAdapter {
	public isup() {
		IRCBot.registerCommand("isup");
	}

	public static boolean ping(String url, int timeout) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			return (200 <= responseCode && responseCode <= 399);
		} catch (IOException exception) {
			return false;
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void onMessage(final MessageEvent event) throws Exception {
		super.onMessage(event);
		String ourinput = event.getMessage().toLowerCase();
		String trigger = ourinput.trim();
		if (trigger.length() > 1) {
			String[] firstWord = StringUtils.split(trigger);
			String triggerWord = firstWord[0];
			if (triggerWord.equals(IRCBot.commandprefix + "isup")) {
				String site = event.getMessage().substring(event.getMessage().indexOf(triggerWord) + triggerWord.length()).trim();
			    if (!site.matches("^(https?)://.*$")) {
			    	site = "http://" + site;
			    }
				String[] schemes = {"http","https"};
				UrlValidator urlValidator = new UrlValidator(schemes);
				if(urlValidator.isValid(site)) {
					boolean rez = ping(site, 1000);
					if (rez) {
						event.respond(site + " Is Up.");
					} else {
						event.respond(site + " Is Down.");
					}
				} else {
					event.respond("Invalid URL");
				}
			}
		}
	}
}