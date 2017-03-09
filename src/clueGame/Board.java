package clueGame;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	 private Map<Character, String> legend;
	 private BoardCell[][] board;
	 private int numRows, numCols;
	 private File layoutFile;
	 private File legendFile;
	 private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	public void setConfigFiles(String layout, String legendFile) {
		this.layoutFile = new File(layout);
		this.legendFile = new File(legendFile);
		
		return;
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		legend = new HashMap<Character, String>();
		BufferedReader br = new BufferedReader(new FileReader(legendFile));
		try {
		String line = br.readLine();
			while (line != null) {
				if (!line.split(",")[2].substring(1).equals("Card")) {
					if (!line.split(",")[2].substring(1).equals("Other")) throw new BadConfigFormatException(legendFile.getName());
				}
				legend.put(line.split(",")[0].toCharArray()[0], line.split(",")[1].substring(1));
				line = br.readLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(layoutFile));
		ArrayList<String> rows = new ArrayList<String>();
		try {
			String line = br.readLine();
			while (line != null) {
				rows.add(line);
				line = br.readLine();
			}
			setNumRows(rows.size());
			setNumCols(rows.get(0).split(",").length);
			board = new BoardCell[getNumRows()][getNumColumns()];
			for (int i = 0; i < getNumRows(); i++) {
				if (rows.get(i).split(",").length != getNumColumns()) throw new BadConfigFormatException(layoutFile.getName());
				for (int j = 0; j < getNumColumns(); j++) {
					String initial = rows.get(i).split(",")[j];
					if (initial.length() > 2) throw new BadConfigFormatException();
					if (legend.get(initial.charAt(0)) == null) throw new BadConfigFormatException(layoutFile.getName());
					board[i][j] = new BoardCell(i,j,initial);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			targets = new HashSet<BoardCell>();
			calcAdjacencies();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	public void setNumRows(int rows) {
		this.numRows = rows;
	}
	public void setNumCols(int cols) {
		this.numCols = cols;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numCols;
	}
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if ((i + 1) < getNumRows()) {
					if ((board[i][j].getInitial() == 'W' || (board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.DOWN)) && board[i+1][j].getInitial() =='W') temp.add(board[i+1][j]);
					else {
						if (board[i+1][j].isDoorway()){
							if (board[i+1][j].getDoorDirection() == DoorDirection.UP)  temp.add(board[i+1][j]);
						}
					}
				}
				if ((i - 1) > - 1) {
					if ((board[i][j].getInitial() == 'W' || (board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.UP)) && board[i-1][j].getInitial() =='W') temp.add(board[i-1][j]);
					else {
						if (board[i-1][j].isDoorway()){
							if (board[i-1][j].getDoorDirection() == DoorDirection.DOWN)  temp.add(board[i-1][j]);
						}
					}
				}
				if ((j + 1) < getNumColumns()) {
					if ((board[i][j].getInitial() == 'W' ||(board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.RIGHT)) && board[i][j+1].getInitial() =='W') temp.add(board[i][j+1]);
					else {
						if (board[i][j+1].isDoorway()){
							if (board[i][j+1].getDoorDirection() == DoorDirection.LEFT)  temp.add(board[i][j+1]);
						}
					}
				}
				if ((j - 1) > - 1) {
					if ((board[i][j].getInitial() == 'W' || (board[i][j].isDoorway() && board[i][j].getDoorDirection() == DoorDirection.LEFT)) && board[i][j-1].getInitial() =='W') temp.add(board[i][j-1]);
					else {
						if (board[i][j-1].isDoorway()){
							if (board[i][j-1].getDoorDirection() == DoorDirection.RIGHT)  temp.add(board[i][j-1]);
						}
					}
				}
				adjMtx.put(board[i][j], temp);
			}
		}
	}
	
	public void calcTargets(int row, int col, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(board[row][col]);
		findAllTargets(board[row][col], pathLength);
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		Set<BoardCell> adjCells = adjMtx.get(thisCell);
		for (BoardCell adjCell : adjCells) {
			if (visited.contains(adjCell)) continue;
			else {
				visited.add(adjCell);
				if (adjCell.isDoorway()) targets.add(adjCell);
				else if (numSteps == 1) targets.add(adjCell);
				else findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMtx.get(board[row][col]);
	}
	
	
}