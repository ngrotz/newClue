package clueGame;

public class BoardCell {
	public int row, column;
	public String initial;
	
	public BoardCell(int row, int column, String initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
	}
	public Boolean isDoorway() {
		return (initial.length() == 2 && !initial.substring(1,2).equals("N"));
	}
	public DoorDirection getDoorDirection(){
		if (isDoorway()) return DoorDirection.getDirection(initial.substring(1, 2));
		else return null;
	}
	public char getInitial() {
		return initial.charAt(0);
	}
	
	public String toString() {
		return String.valueOf(row) + String.valueOf(column);
	}
}
