import kotlin.test.Test
import kotlin.test.assertEquals

class BoxTest {
    @Test
    fun isInRoomTest() {
        assertEquals(true, Box(Coordinates(x = 0, y = 0, z = 0, w = 0, )).isInRoom(Coordinates(x = 0, y = 0, z = 0, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0, )).isInRoom(Coordinates(x = 1, y = 0, z = 0, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0, )).isInRoom(Coordinates(x = 0, y = 1, z = 0, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0, )).isInRoom(Coordinates(x = 0, y = 0, z = 1, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0, )).isInRoom(Coordinates(x = 0, y = 0, z = 0, w = 1)))
    }
}

  /*  class PushTest {
        @Test
        fun testPushBoxNorth() {
            currentLevel = listOf() // 2x2 with all locked doors
            var myBox = Box(1, 1)
            assertEquals(false, myBox.canMove("north"))

            myBox = Box(1,2)
            assertEquals(false, myBox.canMove("south"))

            myBox = Box(2,1)
            assertEquals(false, myBox.canMove("west"))

            myBox = Box(2,2)
            assertEquals(false, myBox.canMove("east"))
        }
}
   */