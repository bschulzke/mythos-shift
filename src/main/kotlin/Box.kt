class Box(val coordinates: Coordinates) : Moveable {
    override fun canMove(direction: String): Boolean {
        val myRoom = currentLevel[coordinates.x!!][coordinates.y!!][coordinates.z!!][coordinates.w!!]!!
        return myRoom.doors.contains(direction) && myRoom.lockCheck(direction)
    }

    override fun move(direction: String) {
        if(direction == "north") {
            coordinates.x = coordinates.x + 1
        }
    }

    override fun failMove(direction: String): String {
        TODO("Not yet implemented")
    }

    override fun isInRoom(playerCoordinates: Coordinates): Boolean {
        return playerCoordinates == coordinates
    }
}