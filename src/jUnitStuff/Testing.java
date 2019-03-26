package jUnitStuff;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Testing {

	@Test
	void testThreeOfAKind() {
		
		System.out.println("TEST 1: testThreeOfAKind()");
		
		int[] rolls = {1,2,3,3,3};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("3 of a Kind", play);
	}
	
	@Test
	void testFourOfAKind() {
		
		System.out.println("TEST 2: testFourOfAKind()");
		
		int[] rolls = {1,3,3,3,3};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("4 of a Kind", play);
	}	
	
	@Test
	void testYahtzee() {
		
		System.out.println("TEST 3: testYahtzee()");
		
		int[] rolls = {3,3,3,3,3};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("YAHTZEE!", play);
	}	
	
	@Test
	void testFullHouse() {
		
		System.out.println("TEST 4: testFullHouse()");
		
		int[] rolls = {2,2,3,3,3};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("Full House", play);
	}	
	
	@Test
	void testSmallStraight() {
		
		System.out.println("TEST 5: testSmallStraight()");
		
		int[] rolls = {1,2,3,4,5};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("Small Straight", play);
	}
	
	@Test
	void testLargeStraight() {
		
		System.out.println("TEST 6: testLargeStraight()");
		
		int[] rolls = {2,3,4,5,6};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("Large Straight", play);
	}
	
	@Test
	void testFalse() {
		
		System.out.println("TEST 6: testFalse()");
		
		int[] rolls = {1,2,3,5,6};
		String play = Stuff2Test.checkPossibilities(rolls);
		
		assertEquals("false", play);
	}
	
}

