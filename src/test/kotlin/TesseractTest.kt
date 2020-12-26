import kotlin.test.Test
import kotlin.test.assertEquals

class TesseractTest {
    @Test
    fun isInRoomTest() {
        assertEquals(true, Tesseract(x = 0, y = 0, z = 0, w = 0..100,).isInRoom(Coordinates(x = 0, y = 0, z = 0, w = 1)))
        assertEquals(false, Tesseract(x = 0, y = 0, z = 0, w = 0..0).isInRoom(Coordinates(x = 1, y = 0, z = 0, w = 0)))
        assertEquals(false, Tesseract(x = 0, y = 0, z = 0, w = 0..0).isInRoom(Coordinates(x = 0, y = 1, z = 0, w = 0)))
        assertEquals(false, Tesseract(x = 0, y = 0, z = 0, w = 0..0).isInRoom(Coordinates(x = 0, y = 0, z = 1, w = 0)))
        assertEquals(false, Tesseract(x = 0, y = 0, z = 0, w = 0..0).isInRoom(Coordinates(x = 0, y = 0, z = 0, w = 1)))
    }
}