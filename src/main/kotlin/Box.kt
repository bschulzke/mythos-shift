class Box(val coordinates: Coordinates) : Moveable {
    override fun canMove(direction: String): Boolean {
        val myRoom = currentLevel.rooms[player.x][player.y][player.z][player.w]!!
        return myRoom.doors.contains(direction) &&
                myRoom.lockCheck(hasLock = myRoom.lock, direction = direction, platePlace = myRoom.link) &&
                destinationNotNull(direction)
    }

    fun destinationNotNull(direction: String) : Boolean {
        if (direction == "north") {
            return currentLevel.rooms[player.x + 1][player.y][player.z][player.w] != null
        } else if (direction == "south") {
            return currentLevel.rooms[player.x - 1][player.y][player.z][player.w] != null
        } else if (direction == "east") {
            return currentLevel.rooms[player.x][player.y + 1][player.z][player.w] != null
        }
        else if (direction == "west") {
            return currentLevel.rooms[player.x][player.y - 1][player.z][player.w] != null
        } else {
            return false
        }
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
        val myRoom = currentLevel.rooms[player.x][player.y][player.z][player.w]!!
        return if (myRoom.doors.contains(direction)) "The door won't open."
        else "There's no door that way."
    }

    override fun isInRoom(playerCoordinates: Coordinates): Boolean {
        return playerCoordinates == coordinates
    }
}