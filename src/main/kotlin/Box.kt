class Box(val coordinates: Coordinates) : Moveable {
    override fun canMove(direction: String): Boolean {
        val myRoom = currentLevel[player.x][player.y][player.z][player.w]!!
        return myRoom.doors.contains(direction) &&
                myRoom.lockCheck(hasLock = myRoom.lock, direction = direction, platePlace = myRoom.link) &&
                player.x + 1 < currentLevel.size &&
                currentLevel[player.x + 1][player.y][player.z][player.w] != null

    }

    override fun move(direction: String) {
        if(direction == "north") {
            coordinates.x = coordinates.x + 1
            player.x = player.x + 1
        }
        else if(direction == "south") {
            coordinates.x = coordinates.x - 1
            player.x = player.x - 1
        }
        else if(direction == "east") {
            coordinates.y = coordinates.y + 1
            player.y = player.y + 1
        }
        else if(direction == "west") {
            coordinates.y = coordinates.y - 1
            player.y = player.y - 1
        }
    }

    override fun failMove(direction: String): String {
        val myRoom = currentLevel[player.x][player.y][player.z][player.w]!!
        return if (myRoom.doors.contains(direction)) "The door won't open."
        else "There's no door that way."
    }

    override fun isInRoom(playerCoordinates: Coordinates): Boolean {
        return playerCoordinates == coordinates
    }
}