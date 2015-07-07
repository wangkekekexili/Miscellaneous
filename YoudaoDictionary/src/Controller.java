import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cache.Cache;
import cache.SimpleCache;

public class Controller implements ActionListener {

	private SimpleGui frame;
	private Cache cache;
	
	public Controller(SimpleGui frame) {
		this.frame = frame;
		cache = new SimpleCache();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		class SearchThread extends Thread {
			private SearchResult result = new SearchResult(
					false, "Search thread error.");
			@Override
			public void run() {
				String wordToSearch = frame.getWordToSearch();
				if (cache.get(wordToSearch) != null) {
					result = new SearchResult(true, 
							cache.get(wordToSearch));
				} else {
					result = Dictionary.search(
							frame.getWordToSearch());
					if (result.hasResult() == true) {
						cache.put(wordToSearch, result.getContent());
					}
				}
			}
			
			public SearchResult getSearchResult() {
				return result;
			}
		}
		
		SearchThread thread = new SearchThread();
		thread.start();
		
		try {
			thread.join();
		} catch (Exception e1) {
			thread.interrupt();
		}
		
		SearchResult result = thread.getSearchResult();
		frame.setResultArea(result);
	}

}
