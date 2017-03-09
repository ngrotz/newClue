package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard(int row, int col) {
		super();
		grid = new BoardCell[row][col];
		for (int i= 0; i<row; i++ ){
			for (int j = 0; j < col; j++ ) {
				grid[i][j] = new BoardCell(row, col);
			}
		}
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Set<BoardCell> adjs = new HashSet<BoardCell>();
				if (i+1 < grid.length) adjs.add(grid[i+1][j]);
				if (i-1 >= 0) adjs.add(grid[i-1][j]);
				if (j+1 < grid[i].length) adjs.add(grid[i][j+1]);
				if (j-1 >= 0) adjs.add(grid[i][j-1]);
				adjMtx.put(grid[i][j], adjs);
			}
		}
		
	}
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		Set<BoardCell> adjCells = adjMtx.get(thisCell);
		for (BoardCell adjCell : adjCells) {
			if (visited.contains(adjCell)) continue;
			else {
				visited.add(adjCell);
				if (numSteps == 1) targets.add(adjCell);
				else findAllTargets(adjCell, numSteps-1);
			}
			visited.remove(adjCell);
		}
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
