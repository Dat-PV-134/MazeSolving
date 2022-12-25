import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
	private Stack<Cell> stack = new Stack<>();
	private Random rand = new Random();
	private int[][] maze;
	private int col, row;

	// Phương thức khởi tạo
	MazeGenerator(int col, int row) {
		this.col = col;
		this.row = row;
		maze = new int[col][row];
	}

	// Phương thức tạo mê cung
	public void generateMaze() {
		stack.push(new Cell(0, 0));
		while (!stack.empty()) {
			Cell next = stack.pop();
			if (validNextCell(next)) {
				maze[next.col][next.row] = 1;
				ArrayList<Cell> neighbors = findNeighbors(next);
				randomlyAddCellsToStack(neighbors);
			}
		}
	}

	// Phương thức lấy ra mê cung dạng mảng 2 chiều gồm các số nhị phân 0 và 1
	public int[][] getBinaryMaze() {
		return maze;
	}

	// Phương thức xác minh vị trí tiếp theo có hợp lệ hay không
	// Không hợp lệ khi vị trí đó có các vị trí liền kề đều là đường đi và vị trí đó
	// là 1 bức tường
	private boolean validNextCell(Cell cell) {
		int numNeighboringOnes = 0;

		// Kiểm tra 4 vị trí liền kề
		// 2 vòng lặp lồng nhau này chạy qua vị trí hiện tại 2 lần
		for (int y = cell.row - 1; y < cell.row + 2; y++) {
			for (int x = cell.col - 1; x < cell.col + 2; x++) {
				if (pointOnGrid(x, y) && pointNotCell(cell, x, y) && maze[x][y] == 1) {
					numNeighboringOnes++;
				}
			}
		}

		return (numNeighboringOnes < 3) && maze[cell.col][cell.row] != 1;
	}

	// Phương thức đẩy ngẫu nhiên một vị trí lên vị trí trên cùng của stack
	private void randomlyAddCellsToStack(ArrayList<Cell> cells) {
		int targetIndex;
		while (!cells.isEmpty()) {
			targetIndex = rand.nextInt(cells.size());
			stack.push(cells.remove(targetIndex));
		}
	}

	// Phương thức trả về các vị trí liền kề của vị trí hiện tại
	private ArrayList<Cell> findNeighbors(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<>();

		// Xét các vị trí liền kề của vị trí hiện tại, nếu hợp lệ thì thêm vào danh sách
		// các vị trí liền kề
		for (int y = cell.row - 1; y < cell.row + 2; y++) {
			for (int x = cell.col - 1; x < cell.col + 2; x++) {
				if (pointOnGrid(x, y) && pointNotCorner(cell, x, y) && pointNotCell(cell, x, y)) {
					neighbors.add(new Cell(x, y));
				}
			}
		}

		return neighbors;
	}

	// Phương thức kiểm tra xem vị trí đang xét có nằm trong mê cung không
	private Boolean pointOnGrid(int x, int y) {
		return x >= 0 && y >= 0 && x < col && y < row;
	}

	// Phương thức kiểm tra xem vị trí đang xét phải là vị trí liền kề vị trí hiện
	// tại không
	private Boolean pointNotCorner(Cell cell, int x, int y) {
		return (x == cell.col || y == cell.row);
	}

	// Phương thức kiểm tra xem vị trí đang xét có khác vị trí hiện tại không
	private Boolean pointNotCell(Cell cell, int x, int y) {
		return !(x == cell.col && y == cell.row);
	}
}
