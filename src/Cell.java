import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Cell extends JButton {

	Cell parent;
	int col;
	int row;
	int gCost;
	int hCost;
	int fCost;
	boolean start;
	boolean goal;
	boolean wall;
	boolean open;
	boolean checked;
	
	// Phương thức khởi tạo
	public Cell(int col, int row) {
		this.col = col;
		this.row = row;
		
		setBackground(Color.white);
		setForeground(Color.black);
	}

	// Phương thức đặt vị trí hiện tại thành điểm bắt đầu
	public void setAsStart() {
		setBackground(Color.green);
		setForeground(Color.white);
		setText("Start");
		start = true;
	}
	
	// Phương thức đặt vị trí hiện tại thành điểm kết thúc
	public void setAsGoal() {
		setBackground(Color.red);
		setForeground(Color.white);
		setText("Goal");
		goal = true;
	}
	
	// Phương thức đặt vị trí hiện tại thành 1 bức tường
	public void setAsWall() {
		setBackground(Color.black);
		setForeground(Color.black);
		wall = true;
	}
	
	// Phương thức xét trạng thái mở của vị trí
	public void setAsOpen() {
		open = true;
	}
	
	// Phương thức xét trạng thái đóng của vị trí
	public void setAsChecked() {
		if (!start && !goal) {
			setBackground(Color.lightGray);
			setForeground(Color.black);
		}
		checked = true;
	}
	
	// Phương thức xét vị trí hiện tại thành 1 phần của con đường tốt nhất
	public void setAsPath() {
		setBackground(Color.cyan);
		setForeground(Color.black);
	}
}
