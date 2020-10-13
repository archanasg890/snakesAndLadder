import org.junit.Assert
import org.junit.Test

class BoardTest {

    @Test
    fun checkPlayerConfig1() {
        // no of players 0
        Configuration(ladders = emptyList(), snakes = emptyList(), players = 0, crookedDice = false).let{ config ->
            try {
                Board(config)
                Assert.assertTrue("Invalid Object Creation", false)
            } catch (e: Exception) {
                Assert.assertEquals(e.message, "Player range should be 1 to 6")
            }
        }
    }

    @Test
    fun checkPlayerConfig2() {
        //no of playes 7
        Configuration(ladders = emptyList(), snakes = emptyList(), players = 7, crookedDice = false).let{ config ->
            try {
                Board(config)
                Assert.assertTrue("Invalid Object Creation", false)
            } catch (e: Exception) {
                Assert.assertEquals(e.message, "Player range should be 1 to 6")
            }
        }
    }

    @Test
    fun checkPlayerConfig3() {
        //Two ladder's bottom at same place
        val ladders = mutableListOf<BoardObjectConfig>()
        ladders.add(BoardObjectConfig(20, 7))
        ladders.add(BoardObjectConfig(28, 7))
        Configuration(ladders = ladders, snakes = emptyList(), players = 2, crookedDice = false).let{ config ->
            try {
                Board(config)
                Assert.assertTrue("Invalid Object Creation", false)
            } catch (e: Exception) {
                Assert.assertEquals(e.message, "Two Objects can not be start at same place")
            }
        }
    }

    @Test
    fun checkPlayerConfig4() {
        //Two snake's top at same place
        val ladders = mutableListOf<BoardObjectConfig>()
        ladders.add(BoardObjectConfig(20, 8))
        ladders.add(BoardObjectConfig(28, 3))
        val snakes = mutableListOf<BoardObjectConfig>()
        snakes.add(BoardObjectConfig(28, 7))
        snakes.add(BoardObjectConfig(28, 11))
        Configuration(ladders = ladders, snakes = snakes, players = 2, crookedDice = false).let{ config ->
            try {
                Board(config)
                Assert.assertTrue("Invalid Object Creation", false)
            } catch (e: Exception) {
                Assert.assertEquals(e.message, "Two Objects can not be start at same place")
            }
        }
    }

    @Test
    fun checkPlayerConfig5() {
        //Two snake's top and ladder's bottom at same place
        val ladders = mutableListOf<BoardObjectConfig>()
        ladders.add(BoardObjectConfig(60, 33))
        ladders.add(BoardObjectConfig(28, 3))
        val snakes = mutableListOf<BoardObjectConfig>()
        snakes.add(BoardObjectConfig(33, 7))
        snakes.add(BoardObjectConfig(28, 11))
        Configuration(ladders = ladders, snakes = snakes, players = 2, crookedDice = false).let{ config ->
            try {
                Board(config)
                Assert.assertTrue("Invalid Object Creation", false)
            } catch (e: Exception) {
                Assert.assertEquals(e.message, "Two Objects can not be start at same place")
            }
        }
    }

    @Test
    fun checkPlayerConfig6() {
        //Crooked dice configuration set to true, and snake and ladder top bottom position not even bumber
        val ladders = mutableListOf<BoardObjectConfig>()
        ladders.add(BoardObjectConfig(60, 33))
        ladders.add(BoardObjectConfig(28, 3))
        val snakes = mutableListOf<BoardObjectConfig>()
        snakes.add(BoardObjectConfig(34, 7))
        snakes.add(BoardObjectConfig(28, 11))
        Configuration(ladders = ladders, snakes = snakes, players = 2, crookedDice = true).let{ config ->
            try {
                Board(config)
                Assert.assertTrue("Invalid Object Creation", false)
            } catch (e: Exception) {
                Assert.assertEquals(e.message, "Objects top bottom places should be even number as board configuration is set to crooked dice")
            }
        }
    }

    @Test
    fun checkDiceVals() {
        //Test for dice value, It should be between 1 to 6
        var diceVal = dice()
        Assert.assertTrue(diceVal > 0 && diceVal <= 6)
        diceVal = dice()
        Assert.assertTrue(diceVal > 0 && diceVal <= 6)
    }

    @Test
    fun checkCrookedDiceVals() {
        //Test for crooked dice, dice value should be 2, 4, 6
        var diceVal = dice(true)
        Assert.assertTrue(diceVal != 0 && diceVal <= 6 && diceVal%2 == 0)
        diceVal = dice(true)
        Assert.assertTrue(diceVal != 0 && diceVal <= 6 && diceVal%2 == 0)
    }
}