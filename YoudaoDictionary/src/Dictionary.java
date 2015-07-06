import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Use Cambridge web service to translate from English to simplified Chinese.
 * 
 * @author kewang
 *
 */
public class Dictionary {

	public static final String KEY_FROM = "KeAndYiyangsFamily";
	public static final String API_KEY = "1266068425";
	public static final String YOUDAO_DICT_URL = 
			"http://fanyi.youdao.com/openapi.do?"
			+ "keyfrom=KeAndYiyangsFamily"
			+ "&key=1266068425"
			+ "&type=data"
			+ "&doctype=json"
			+ "&version=1.1"
			+ "&only=dict"
			+ "&q=";
	
	public static final String getJsonString(String url) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new URL(url).openStream(), "UTF-8"));
			String content = br.readLine();
			br.close();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String searchForDefination(String word) {
		try {
			JsonNode rootNode = new ObjectMapper().readTree(getJsonString(
					YOUDAO_DICT_URL + word));
			JsonNode defination = rootNode.path("basic").path("explains");
			if (defination.size() <= 0) {
				return "";
			}
			StringBuilder result = new StringBuilder();
			for (int i = 0;i != defination.size();i++) {
				result.append((i+1));
				result.append(":");
				result.append(defination.get(i).toString().replace('"', ' '));
				result.append("\n");
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
