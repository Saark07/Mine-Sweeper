package mines;

import java.util.Random;

public class Mines {
	private int height;
	private int width;
	private int numMines;
	private boolean showAll;
	private Cube[][] board; // board of cubes

	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		board = new Cube[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				board[i][j] = new Cube(); // place in each place in the board a cube
		Random r = new Random();
		for (int i = 0; i < numMines; i++)
			board[r.nextInt(height)][r.nextInt(width)].setMine(true);// place in randomized place mines in the
																		// boundaries of the board
	}

	public class Cube { // an inside class that will include information about the specific cube
		private boolean flag;
		private boolean mine;
		private boolean open;

		public boolean isOpen() { // return true\false if Cube is open
			return open;
		}

		public void setOpen(boolean open) { // place in the cube the state of it
			this.open = open;
		}

		public boolean isFlag() { // return true\false if flag is applied
			return flag;
		}

		public void setFlag(boolean flag) {// place in the cube the state of the flag
			this.flag = flag;
		}

		public boolean isMine() { // return true\false if mine is place
			return mine;
		}

		public void setMine(boolean mine) {// place in the cube the state of mine
			this.mine = mine;
		}

	}

	public boolean addMine(int i, int j) {
		if (!board[i][j].isMine()) { // if mine is not placed, set mine and add 1 to the number of mines
			board[i][j].setMine(true);
			numMines++;
			return true;
		}
		return false;

	}

	public boolean open(int i, int j) {
		boolean[][] visited = new boolean[height][width];
		// boolean matrix that simulated the cubes that were visited
		if (board[i][j].isMine()) {
			return false;
		}
		return checkNeighbors(i, j, visited);
	}

	public void toggleFlag(int x, int y) {
		board[x][y].setFlag(!board[x][y].isFlag());
	}

	public boolean isDone() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (!board[i][j].isMine() && !board[i][j].isOpen())
					return false;
		return true;
	}

	public String get(int i, int j) {
		
		if (!board[i][j].isOpen() && !showAll) { // check if the cube is closed and check if there is a flag
			if (board[i][j].isFlag()) // if there is flag in the spot place "F" otherwise place "."
				return "F";
			else
				return ".";
		} else if (board[i][j].isMine()) // if cube is open and there is a mine in spot than place "X"
			// otherwise place " " if number of mines=0 and if not then place the number of
			// mines.
			return "X";
		else if (numOfMines(i, j) == 0)
			return " ";
		return "" + numOfMines(i, j);
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public String toString() { // building string of the board and print it
		StringBuilder st = new StringBuilder("");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				st.append(get(i, j));
			st.append("\n");
		}
		return st.toString();
	}

	// Function that check if all of (i,j) node neighbors are not mines,and if they
	// are not, call recursively to this function again.
	private boolean checkNeighbors(int i, int j, boolean[][] visited) {

		// Check for height border of the board
		if (i >= height || i < 0)
			return false;

		// Check for width border of the board
		if (j >= width || j < 0)
			return false;

		// Mark (i,j) if it's visited already
		if (visited[i][j] == true)
			return false;
		else
			visited[i][j] = true;

		board[i][j].setOpen(true); // Set (i,j) to be open

		if (board[i][j].isMine()) // Check if (i,j) is mine
			return false;

		// If all of the neighbors are not mine visit all of them recursively
		if (numOfMines(i, j) == 0) {
			checkNeighbors(i + 1, j, visited);
			checkNeighbors(i - 1, j, visited);
			checkNeighbors(i, j + 1, visited);
			checkNeighbors(i, j - 1, visited);
			checkNeighbors(i + 1, j + 1, visited);
			checkNeighbors(i + 1, j - 1, visited);
			checkNeighbors(i - 1, j - 1, visited);
			checkNeighbors(i - 1, j + 1, visited);
		}
		return true;
	}

	private int numOfMines(int i, int j) { // check all the mine in the area of i,j
		// there are 8 cubes around i,j
		int cnt = 0; // counter
		if (i + 1 < height)
			if (board[i + 1][j].isMine())
				cnt++;
		if (i - 1 >= 0)
			if (board[i - 1][j].isMine())
				cnt++;
		if (j + 1 < width)
			if (board[i][j + 1].isMine())
				cnt++;
		if (j - 1 >= 0)
			if (board[i][j - 1].isMine())
				cnt++;
		if (i + 1 < height && j + 1 < width)
			if (board[i + 1][j + 1].isMine())
				cnt++;
		if (i + 1 < height && j - 1 >= 0)
			if (board[i + 1][j - 1].isMine())
				cnt++;
		if (i - 1 >= 0 && j - 1 >= 0)
			if (board[i - 1][j - 1].isMine())
				cnt++;
		if (i - 1 >= 0 && j + 1 < width)
			if (board[i - 1][j + 1].isMine())
				cnt++;

		return cnt;
	}

	public Cube getBoard(int i, int j) {
		return board[i][j];
	}
}