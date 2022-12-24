import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Panel extends JPanel {
	
	// Cài đặt màn hình
	final int maxCol = 30;
	final int maxRow = 30;
	final int cellSize = 20;
	final int screenWidth = (cellSize * maxCol);
	final int screenHeight = (cellSize * maxRow);
	
	// Tạo mê cung
	MazeGenerator mazeGenerator = new MazeGenerator(30);
	Cell[][] cells = new Cell[maxCol][maxRow];
	Cell startCell, goalCell, currentCell;
	int[][] mazeInBinary;
	
	ArrayList<Cell> openList = new ArrayList<>();
	ArrayList<Cell> checkedList = new ArrayList<>();

	boolean goalReached = false;
	
	// Phương thức khởi tạo
	public Panel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setLayout(new GridLayout(maxRow, maxCol));
		this.addKeyListener(new KHandler(this));
		this.setFocusable(true);
		
		// Tạo mê cung dạng mảng 2 chiều tạo thành từ hai số nhị phân 0 và 1
		mazeGenerator.generateMaze();
		mazeInBinary = mazeGenerator.getRawMaze();
		
		// Khởi tạo các vị trí trong mê cung
		int col = 0;
		int row = 0;
		
		while (col < maxCol && row < maxRow) {
			cells[col][row] = new Cell(col, row);
			this.add(cells[col][row]);
			
			col++;
			if (col == maxCol) {
				col = 0;
				row++;
			}
		}
		
		// Xác định điểm bắt đầu và điểm kết thúc
		setStartCell();
		setGoalCell();
		
		// Xây dựng các bức tường
		setWallCell();
		
		// Hiển thị các chi phí g, h, f lên từng vị trí trong mê cung
		setCostOnCells();
	}
	
	// Phương thức xét 1 vị trí bắt đầu ngẫu nhiên
	private void setStartCell() {
		Random random = new Random();
		int col = random.nextInt(30);
		int row = random.nextInt(30);
		
		if (mazeInBinary[col][row] == 0) {
			setStartCell();
			return;
		}
		
		cells[col][row].setAsStart();
		startCell = cells[col][row];
		currentCell = startCell;
	}
	
	// Phương thức xét 1 vị trí đích ngẫu nhiên
	private void setGoalCell() {
		Random random = new Random();
		int col = random.nextInt(30);
		int row = random.nextInt(30);
		
		if (mazeInBinary[col][row] == 0) {
			setGoalCell();
			return;
		}
		
		cells[col][row].setAsGoal();
		goalCell = cells[col][row];
		
		if (goalCell == startCell) {
			setGoalCell();
		}
	}
	
	// Phương thức tạo các bức tường
	private void setWallCell() {
		int col = 0;
		int row = 0;
		
		while (col < maxCol && row < maxRow) {
			if (mazeInBinary[col][row] == 0) {
				cells[col][row].setAsWall();
			}
			
			col++;
			if (col == maxCol) {
				col = 0;
				row++;
			}
		}
	}
	
	// Phương thức lấy ra các chi phí g, h, f của từng vị trí trong mê cung
	private void getCost(Cell cell) {
		
		// Chi phí gCost - Khoảng cách từ vị trí đang xét đến điểm bắt đầu
		int xDistance = Math.abs(cell.col - startCell.col);
		int yDistance = Math.abs(cell.row - startCell.row);
		cell.gCost = xDistance + yDistance;
		
		// Chi phí hCost - Khoảng cách từ vị trí đang xét đến điểm kết đích
		xDistance = Math.abs(cell.col - goalCell.col);
		yDistance = Math.abs(cell.row - goalCell.row);
		cell.hCost = xDistance + yDistance;
		
		// Chi phí fCost - Tổng giá trị của gCost và hCost
		cell.fCost = cell.gCost + cell.hCost;
		
		// Hiển thị các chi phí trên từng vị trí
		if (cell != startCell && cell != goalCell) {
			//cell.setText("<html>g: " + cell.gCost + " h: " + cell.hCost + "<br>f: " + cell.fCost);
		}
	}
	
	// Phương thức đặt các chi phí cho từng vị trí
	private void setCostOnCells() {
		int col = 0;
		int row = 0;
		
		while (col < maxCol && row < maxRow) {
			getCost(cells[col][row]);
			col++;
			
			if (col == maxCol) {
				col = 0;
				row++;
			}
		}
	}
	
	// Thuật toán A* để tìm đường đi từ điểm bắt đầu đến điểm đích
	public void aStar() {
		while (!goalReached) {
			
			int col = currentCell.col;
			int row = currentCell.row;
			
			currentCell.setAsChecked();
			checkedList.add(currentCell);
			openList.remove(currentCell);
			
			// Mở vị trí bên trên
			if (row - 1 >= 0) {
				openCell(cells[col][row - 1]);
			}
			
			// Mở vị trí bên dưới
			if (row + 1 < maxRow) {
				openCell(cells[col][row + 1]);
			}
			
			// Mở vị trí bên trái
			if (col - 1 >= 0) {
				openCell(cells[col - 1][row]);
			}
		
			// Mở vị trí bên phải
			if (col + 1 < maxCol) {
				openCell(cells[col + 1][row]);
			}
			
			int bestCellIndex = 0;
			int bestCellFCost = 999;
			
			for (int i = 0; i < openList.size(); i++) {
				
				// Kiểm tra các vị trí trong danh sách mở có vị trí nào có chi phí fCost tốt hơn không
				if (openList.get(i).fCost < bestCellFCost) {
					bestCellIndex = i;
					bestCellFCost = openList.get(i).fCost;
				}
				
				// Nếu chi phí fCost bằng nhau thì kiểm tra gCost
				else if (openList.get(i).fCost == bestCellFCost) {
					if (openList.get(i).gCost < openList.get(bestCellIndex).gCost) {
						bestCellIndex = i;
					}
				}
			}
			
			// Sau vòng lặp chúng ta sẽ có được vị trí tốt nhất cho bước đi tiếp theo
			currentCell = openList.get(bestCellIndex);
			
			// Nếu đã đến đích thì dừng lại và tiến hành tô màu đường đi tốt nhất
			if (currentCell == goalCell) {
				goalReached = true;
				trackThePath();
			}
		}
	}
	
	// Phương thức thêm vị trí vào danh sách các vị trí mở
	private void openCell(Cell cell) {
		if (cell.open == false && cell.checked == false && cell.wall == false) {
			cell.setAsOpen();
			cell.parent = currentCell;
			openList.add(cell);
		}
	}
	
	// Phương thức quay lui để tìm con đường tốt nhất
	private void trackThePath() {
		Cell current = goalCell;
		
		while (current != startCell) {
			current = current.parent;
			
			if (current != startCell) {
				current.setAsPath();
			}
		}
	}
}
