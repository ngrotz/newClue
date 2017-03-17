package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class MKBS_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("MKBS_ClueLayout.csv", "MKBS_ClueLegend.txt", "NGKM_Person.txt", "NGKM_Weapon.txt");
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(30, 20);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(19, 19);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(25, 5);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(2 , 16);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(14, 1);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(30, 12);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		
		// TEST DOORWAY LEFT 
		Set<BoardCell> testList = board.getAdjList(8, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(4, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 12)));
				
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(16, 2);
		assertTrue(testList.contains(board.getCellAt(16, 1)));
		assertTrue(testList.contains(board.getCellAt(16, 3)));
		assertTrue(testList.contains(board.getCellAt(15, 2)));
		assertTrue(testList.contains(board.getCellAt(17, 2)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 12);
		assertTrue(testList.contains(board.getCellAt(5, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertTrue(testList.contains(board.getCellAt(4, 12)));
		assertTrue(testList.contains(board.getCellAt(6, 12)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(8, 15);
		assertTrue(testList.contains(board.getCellAt(7, 15)));
		assertTrue(testList.contains(board.getCellAt(9, 15)));
		assertTrue(testList.contains(board.getCellAt(8, 16)));
		assertTrue(testList.contains(board.getCellAt(8, 14)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(21, 13);
		assertTrue(testList.contains(board.getCellAt(20, 13)));
		assertTrue(testList.contains(board.getCellAt(22, 13)));
		assertTrue(testList.contains(board.getCellAt(21, 12)));
		assertTrue(testList.contains(board.getCellAt(21, 14)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are PINK on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of a room 
		Set<BoardCell> testList = board.getAdjList(21, 16);
		assertTrue(testList.contains(board.getCellAt(21, 15)));
		assertTrue(testList.contains(board.getCellAt(21, 17)));
		assertTrue(testList.contains(board.getCellAt(20, 16)));
		assertEquals(3, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(7, 4);
		assertTrue(testList.contains(board.getCellAt(7, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 5)));
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		assertTrue(testList.contains(board.getCellAt(8, 4)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(30, 2);
		assertTrue(testList.contains(board.getCellAt(29, 2)));
		assertTrue(testList.contains(board.getCellAt(30, 3)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(27, 19);
		assertTrue(testList.contains(board.getCellAt(27, 18)));
		assertTrue(testList.contains(board.getCellAt(27, 20)));
		assertTrue(testList.contains(board.getCellAt(26, 19)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(9, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 8)));
		assertTrue(targets.contains(board.getCellAt(8, 8)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 9)));
		
		board.calcTargets(16, 17, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 16)));
			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 2, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 2)));
		assertTrue(targets.contains(board.getCellAt(1, 3)));
		
		board.calcTargets(22, 13, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 12)));
		assertTrue(targets.contains(board.getCellAt(21, 14)));	
		assertTrue(targets.contains(board.getCellAt(20, 13)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(0, 10, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 9)));
		assertTrue(targets.contains(board.getCellAt(4, 10)));
		
		
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(5, 20, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(22, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly left/right
		assertTrue(targets.contains(board.getCellAt(22, 5)));
		assertTrue(targets.contains(board.getCellAt(22, 9)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(24, 7)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(23, 6)));
		assertTrue(targets.contains(board.getCellAt(23, 8)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));
		assertTrue(targets.contains(board.getCellAt(21, 8)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(15, 2, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(13, 2)));
		assertTrue(targets.contains(board.getCellAt(17, 2)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(16, 3)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(14, 3)));
		// into the room
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(16, 1)));
				
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(16, 17, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 16)));
		// Take two steps
		board.calcTargets(22, 13, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 12)));
		assertTrue(targets.contains(board.getCellAt(21, 14)));	
		assertTrue(targets.contains(board.getCellAt(20, 13)));		
	}

}
