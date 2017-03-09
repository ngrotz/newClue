package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	private static IntBoard board;

	
	@Before 
	public void setUp() {
		board = new IntBoard(4, 4);
	}

	// Tests adjacency list for top left corner
	@Test
	public void testAdjacencyTPC() {
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	//Tests adjacency for bottom right corner
	@Test
	public void testAdjacencyBRC() {
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	//Tests adjacency for a right edge
	@Test
	public void testAdjacencyRE() {
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	//Tests adjacency for a left edge
	@Test
	public void testAdjacencyLE() {
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(2, testList.size());
	}
		
	//Tests adjacency for second column middle of grid
	@Test
	public void testAdjacencyC2M() {
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4, testList.size());
	}
	
	//Tests adjacency for third column middle of grid
	@Test
	public void testAdjacencyC3M() {
		BoardCell cell = board.getCell(2,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	// Test calc targets for corner, 3 step
	@Test
	public void testTargetC3() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	// Test calc targets for corner, 1 step
	@Test
	public void testTargetC1() {
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}

	// Test calc targets for left edge, 1 step
	@Test
	public void testTargetLE1() {
		BoardCell cell = board.getCell(2, 0);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	// Test calc targets for right edge, 2 steps
	@Test
	public void testTargetRE2() {
		BoardCell cell = board.getCell(1, 3);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	// Test calc targets for middle, 1 step
	@Test
	public void testTargetM1() {
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	// Test calc targets for middle, 2 steps
	@Test
	public void testTargetM2() {
		BoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
	}

}
