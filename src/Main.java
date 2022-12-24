import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		// Tạo 1 cửa sổ để hiển thị trò chơi
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setResizable(false);
		window.add(new Panel());
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
