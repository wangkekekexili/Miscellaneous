import java.util.StringJoiner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Use Cambridge web service to translate from English to simplified Chinese.
 * 
 * @author kewang
 *
 */
public class EnglishChineseDictionary {

	private static final String URL = "http://dictionary.cambridge.org/us/dictionary/english-chinese-simplified/";
	
	public static final void showUsage() {
		System.out.println("usage: dict [word]");
		System.exit(1);
	}
	
	public static final void showNoResult() {
		System.out.println("No result.");
		System.exit(1);
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			showUsage();
		}
		StringJoiner joiner = new StringJoiner("_");
		for (String argument: args) {
			joiner.add(argument);
		}
		final String wordToSearch = joiner.toString();
		
		Document document = null;
		try {
			document = Jsoup.connect(URL+wordToSearch).get();
		} catch (Exception e) {
			showNoResult();
		}
		Elements titleElements = document
				.getElementsByAttributeValue("class", "di-title cdo-section-title-hw");
		if (titleElements.size() == 0) {
			showNoResult();
		}
		System.out.println(titleElements.get(0).text());
		
		Elements definationBodies = document
				.getElementsByAttributeValue("class", "def-body");
		for (int i = 0;i != definationBodies.size();i++) {
			System.out.print(Integer.toString(i+1));
			System.out.print(": ");
			System.out.println(definationBodies.get(i)
					.getElementsByTag("div").get(0)
					.text().trim());
		}
		
	}

}
