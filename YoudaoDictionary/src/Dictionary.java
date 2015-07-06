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
	
	public static String searchForDefination(String word) {
		return "";
	}

}
