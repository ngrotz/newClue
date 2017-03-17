package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class MKBS_FileInitTests {
	
	public static final int LEGEND_SIZE =11;
	public static final int NUM_ROWS = 31;
	public static final int NUM_COLUMNS = 21;

	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("MKBS_ClueLayout.csv", "MKBS_ClueLegend.txt", "NGKM_Person.txt", "NGKM_Weapon.txt");	
		// Initialize will load BOTH config files 
		board.initialize();
	}

	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Ticket Booth", legend.get('T'));
		assertEquals("Concessions Stand", legend.get('C'));
		assertEquals("Theater F", legend.get('F'));
		assertEquals("Movie Display", legend.get('X'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(15, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(4, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(16, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(22, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(18, 10);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(14, 14);
		assertFalse(cell.isDoorway());
	}
	
	// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(12, numDoors);
		}
		
		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('T', board.getCellAt(0, 4).getInitial());
			assertEquals('C', board.getCellAt(14, 10).getInitial());
			assertEquals('P', board.getCellAt(0, 0).getInitial());
			// Test last cell in room
			assertEquals('E', board.getCellAt(4, 20).getInitial());
			// Test a walkway
			assertEquals('W', board.getCellAt(0, 2).getInitial());
			// Test the movie display
			assertEquals('X', board.getCellAt(13,4).getInitial());
		}

}
