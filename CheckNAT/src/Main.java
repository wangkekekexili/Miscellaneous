import java.net.InetAddress;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Check if behind NAT.
 * 
 * @author kewang
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		// get local IP address
		InetAddress local = InetAddress.getLocalHost();
		
		// get IP address from www.whatismyip.com
		Document document = Jsoup.connect("http://www.whatismyip.com")
				.timeout(5000).get();
		String ip = document.getElementById("ip-box")
				.getElementsByClass("ip").get(0)
				.getElementsByTag("div").get(1)
				.text();
		
		if (local.getHostAddress().equals(ip)) {
			System.out.println("no");
		} else {
			System.out.println("yes");
		}
	}

}
