import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
	 private Stack<Cell> stack = new Stack<>();
	    private Random rand = new Random();
	    private int[][] maze;
	    private int dimension;

	    MazeGenerator(int dim) {
	        maze = new int[dim][dim];
	        dimension = dim;
	    }

	    public void generateMaze() {
	        stack.push(new Cell(0,0));
	        while (!stack.empty()) {
	            Cell next = stack.pop();
	            if (validNextNode(next)) {
	                maze[next.row][next.col] = 1;
	                ArrayList<Cell> neighbors = findNeighbors(next);
	                randomlyAddNodesToStack(neighbors);
	            }
	        }
	    }

	    public int[][] getRawMaze() {
	    	return maze;
	    }

	    public String getSymbolicMaze() {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < dimension; i++) {
	            for (int j = 0; j < dimension; j++) {
	                sb.append(maze[i][j] == 1 ? "*" : " ");
	                sb.append("  "); 
	            }
	            sb.append("\n");
	        }
	        return sb.toString();
	    }

	    private boolean validNextNode(Cell node) {
	        int numNeighboringOnes = 0;
	        for (int y = node.row-1; y < node.row+2; y++) {
	            for (int x = node.col-1; x < node.col+2; x++) {
	                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && maze[y][x] == 1) {
	                    numNeighboringOnes++;
	                }
	            }
	        }
	        return (numNeighboringOnes < 3) && maze[node.row][node.col] != 1;
	    }

	    private void randomlyAddNodesToStack(ArrayList<Cell> nodes) {
	        int targetIndex;
	        while (!nodes.isEmpty()) {
	            targetIndex = rand.nextInt(nodes.size());
	            stack.push(nodes.remove(targetIndex));
	        }
	    }

	    private ArrayList<Cell> findNeighbors(Cell node) {
	        ArrayList<Cell> neighbors = new ArrayList<>();
	        for (int y = node.row-1; y < node.row+2; y++) {
	            for (int x = node.col-1; x < node.col+2; x++) {
	                if (pointOnGrid(x, y) && pointNotCorner(node, x, y)
	                    && pointNotNode(node, x, y)) {
	                    neighbors.add(new Cell(x, y));
	                }
	            }
	        }
	        return neighbors;
	    }

	    private Boolean pointOnGrid(int x, int y) {
	        return x >= 0 && y >= 0 && x < dimension && y < dimension;
	    }

	    private Boolean pointNotCorner(Cell node, int x, int y) {
	        return (x == node.col || y == node.row);
	    }
	    
	    private Boolean pointNotNode(Cell node, int x, int y) {
	        return !(x == node.col && y == node.row);
	    }
}
