import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KHandler implements KeyListener {

	Panel panel;
	
	public KHandler(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		// Lắng nghe sự kiện nhấn nút Enter và bắt đầu thực hiện tìm kiếm đường đi tốt nhất
		if (code == KeyEvent.VK_ENTER) {
			panel.aStar();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
