class Tesseract(val x: Int, val y: Int, val z: Int, val w: ClosedRange<Int>) : Moveable {
    override fun canMove(direction: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun move(direction: String) {
        TODO("Not yet implemented")
    }

    override fun failMove(direction: String): String {
        TODO("Not yet implemented")
    }

    override fun isInRoom(playerCoordinates: Coordinates): Boolean {
        return x == playerCoordinates.x && y == playerCoordinates.y && z == playerCoordinates.z && w.contains(playerCoordinates.w!!)
    }
}