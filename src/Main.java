import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		// Tạo 1 cửa sổ để hiển thị trò chơi
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.add(new Panel());

		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
