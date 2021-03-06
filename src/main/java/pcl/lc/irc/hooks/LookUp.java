package pcl.lc.irc.hooks;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import pcl.lc.irc.Config;
import pcl.lc.irc.IRCBot;

/**
 * @author Caitlyn
 *
 */
@SuppressWarnings("rawtypes")
public class LookUp extends ListenerAdapter {
	public LookUp() {
		IRCBot.registerCommand("lookup");
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void onMessage(final MessageEvent event) throws Exception {
		super.onMessage(event);
		String prefix = Config.commandprefix;
		String ourinput = event.getMessage().toLowerCase();
		String trigger = ourinput.trim();
		if (trigger.length() > 1) {
			String[] message = StringUtils.split(trigger);
			String triggerWord = message[0];
			if (triggerWord.equals(prefix + "lookup")) {
				if (!IRCBot.isIgnored(event.getUser().getNick())) {
					InetAddress[] inetAddressArray = InetAddress.getAllByName(message[1]);
					String output = "DNS Info for " + message[1] + " ";
					for (int i = 0; i < inetAddressArray.length; i++) {
						output += inetAddressArray[i];
					}
					event.respond(output.replace(message[1] + "/", " ").replaceAll("((?::0\\b){2,}):?(?!\\S*\\b\\1:0\\b)(\\S*)", "::$2"));
				}
			} else if (triggerWord.equals(prefix + "rdns")) {
				if (!IRCBot.isIgnored(event.getUser().getNick())) {
					InetAddress addr = InetAddress.getByName(message[1]);
					String host = addr.getCanonicalHostName();
					String output = "Reverse DNS Info for " + message[1] + " " + host;
					event.respond(output);
				}
			}		
		}
	}
}
