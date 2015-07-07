import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SimpleGui extends JFrame {
	
	private JTextField searchTextField;
	private JTextArea resultTextArea;
	public SimpleGui() {
		
		Controller listener = new Controller(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		setSize(240, 320);
		getContentPane().setLayout(null);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(40, 5, 160, 30);
		getContentPane().add(searchTextField);
		
		searchTextField.addActionListener(listener);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(listener);
		searchButton.setBounds(62, 40, 117, 29);
		getContentPane().add(searchButton);
		
		resultTextArea = new JTextArea();
		resultTextArea.setText("");
		resultTextArea.setBounds(6, 81, 228, 211);
		getContentPane().add(resultTextArea);
	}

	public String getWordToSearch() {
		return searchTextField.getText();
	}
	
	public void setResultArea(SearchResult result) {
		if (result.hasResult() == true) {
			resultTextArea.setForeground(Color.BLACK);
			resultTextArea.setText(result.getContent());
		} else {
			resultTextArea.setForeground(Color.RED);
			resultTextArea.setText(result.getContent());
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new SimpleGui().setVisible(true);;
		});
	}
}
