import kotlin.test.Test
import kotlin.test.assertEquals

class BoxTest {
    @Test
    fun isInRoomTest() {
        assertEquals(true, Box(Coordinates(x = 0, y = 0, z = 0, w = 0,)).isInRoom(Coordinates(x = 0, y = 0, z = 0, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0,)).isInRoom(Coordinates(x = 1, y = 0, z = 0, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0,)).isInRoom(Coordinates(x = 0, y = 1, z = 0, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0,)).isInRoom(Coordinates(x = 0, y = 0, z = 1, w = 0)))
        assertEquals(false, Box(Coordinates(x = 0, y = 0, z = 0, w = 0,)).isInRoom(Coordinates(x = 0, y = 0, z = 0, w = 1)))
    }
}