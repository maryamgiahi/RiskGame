package com.playerparser;

import static org.junit.Assert.*;
import org.junit.*;

import com.entity.Country;
import com.entity.Player;
import com.mapparser.MapVerifier;
import com.playerparser.PlayerCommands;

/**
 * This is the test class for PlayerCommands
 * 
 * @author Maryam
 * @version 0.0.1
 */
public class PlayerCommandsTest {
	Player player;
	PlayerCommands playerCmd;
	
	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeAllTesting() {
		System.out.println("This is for testing Player Class");
	}

	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		player = new Player(4, "TestPlayer");
		playerCmd = new PlayerCommands();
	}

	/**
	 * This method runs After All Testing
	 */
	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("The tests are done");
	}
	
	/**
	 * This method tests 3 reinforce armies
	 */
	@Test
	public void testThreeReinforceArmiesForPLayer() {
		
		for (int idx = 0; idx < 8; idx++)
			player.getAssignedCountry().add(new Country());
		
		int armies = playerCmd.countReinforcementArmies(player);
		assertEquals(3, armies);
	}

	/**
	 * This method tests more than 3 reinforce armies
	 */
	@Test
	public void testReinforceArmiesCountForPLayer() {
		
		for (int idx = 0; idx < 25; idx++)
			player.getAssignedCountry().add(new Country());
		
		int armies = playerCmd.countReinforcementArmies(player);
		assertEquals(8, armies);
	}
}

