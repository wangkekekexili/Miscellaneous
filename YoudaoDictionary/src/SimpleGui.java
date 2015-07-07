import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SimpleGui extends JFrame {
	
	private JTextField wordToSearch;
	private JTextArea resultArea;
	public SimpleGui() {
		
		class SearchListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				class SearchThread extends Thread {
					private SearchResult result = new SearchResult(
							false, "Search thread error.");
					@Override
					public void run() {
						result = Dictionary.search(
								wordToSearch.getText());
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
				
				if (result.hasResult() == true) {
					resultArea.setForeground(Color.BLACK);
					resultArea.setText(result.getContent());
				} else {
					resultArea.setForeground(Color.RED);
					resultArea.setText(result.getContent());
				}
				
			}
		}
		
		SearchListener listener = new SearchListener();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		setSize(240, 320);
		getContentPane().setLayout(null);
		
		wordToSearch = new JTextField();
		wordToSearch.setBounds(40, 5, 160, 30);
		getContentPane().add(wordToSearch);
		
		wordToSearch.addActionListener(listener);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(listener);
		searchButton.setBounds(62, 40, 117, 29);
		getContentPane().add(searchButton);
		
		resultArea = new JTextArea();
		resultArea.setText("");
		resultArea.setBounds(6, 81, 228, 211);
		getContentPane().add(resultArea);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new SimpleGui().setVisible(true);;
		});
	}
}
