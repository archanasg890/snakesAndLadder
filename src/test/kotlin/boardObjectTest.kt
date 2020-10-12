import org.junit.Assert
import org.junit.Test

class BoardObjectTest {

    @Test
    fun testObjectsAreInLine() {
        Assert.assertTrue(isOnSameLine(20, 12))
        Assert.assertFalse(isOnSameLine(30, 10))
    }

    @Test
    fun testObjectBasicValidation1() {
        //Top should be greater than 10
        try {
            Snake(10, 2)
            Assert.assertTrue("Invalid Object Creation", false)
        } catch (e: Exception) {
            Assert.assertTrue(e.message == "Invalid Object, as top should be greater than 10")
        }
    }

    @Test
    fun testObjectBasicValidation2() {
        //Top should be greater than bottom
        try {
            Snake(11, 22)
            Assert.assertTrue("Invalid Object Creation", false)
        } catch (e: Exception) {
            Assert.assertTrue(e.message == "Invalid Object, top should be greater than bottom")
        }
    }

    @Test
    fun testObjectBasicValidation3() {
        //Top should not be 100
        try {
            Snake(100, 22)
            Assert.assertTrue("Invalid Object Creation", false)
        } catch (e: Exception) {
            Assert.assertTrue(e.message == "Ladder top or snack mouth should not be more than 99")
        }
    }

    @Test
    fun testObjectBasicValidation4() {
        //Top and bottom should not be on same line
        try {
            Snake(99, 91)
            Assert.assertTrue("Invalid Object Creation", false)
        } catch (e: Exception) {
            Assert.assertTrue(e.message == "Invalid Object, top and bottom should be on same line")
        }
    }

    @Test
    fun testSnakeObjectAndMove() {
        try {
            Snake(99,34).let{
                Assert.assertTrue("Snake Object has been created successfully", true)
                Assert.assertTrue(it.move() == 34)
            }
        } catch (e: Exception) {
            Assert.assertTrue(e.message, false)
        }
    }

    @Test
    fun testLadderObjectAndMove() {
        try {
            Ladder(96,34).let{
                Assert.assertTrue("Ladder Object has been created successfully", true)
                Assert.assertTrue(it.move() == 96)
            }
        } catch (e: Exception) {
            Assert.assertTrue(e.message, false)
        }
    }
}