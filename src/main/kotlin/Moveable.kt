interface Moveable {
    fun canMove(direction: String): Boolean
    fun move(direction: String)
    fun failMove(direction: String): String
    fun isInRoom(coordinates: Coordinates): Boolean
}