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
	
	public static String searchForDefination(String word) {
		Document document = null;
		try {
			document = Jsoup.connect(URL+word).get();
		} catch (Exception e) {
			return e.getMessage();
		}
		Elements titleElements = document
				.getElementsByAttributeValue("class", "di-title cdo-section-title-hw");
		if (titleElements.size() == 0) {
			return "No result found on Cambridge English Chinese dictionary.";
		}
		
		StringBuilder resultBuilder = new StringBuilder();
		Elements definationBodies = document
				.getElementsByAttributeValue("class", "def-body");
		for (int i = 0;i != definationBodies.size();i++) {
			resultBuilder.append(Integer.toString(i+1));
			resultBuilder.append(": ");
			resultBuilder.append(definationBodies.get(i)
					.getElementsByTag("div").get(0)
					.text().trim());
			resultBuilder.append("\n");
		}
		
		return resultBuilder.toString();
	}

}
