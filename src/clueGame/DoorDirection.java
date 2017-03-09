package clueGame;

public enum DoorDirection {

	UP("U"),DOWN("D"),RIGHT("R"),LEFT("L");
	
	private String value;
	
	DoorDirection(String s) {
		value = s;
	}
	
	public static DoorDirection getDirection(String s) {
		switch (s) {
		case "U": return DoorDirection.UP;
		case "D": return DoorDirection.DOWN;
		case "R": return DoorDirection.RIGHT;
		default: return DoorDirection.LEFT;
		}
	}
}
