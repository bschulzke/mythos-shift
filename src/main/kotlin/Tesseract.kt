class Tesseract(var x: Int, var y: Int, var z: Int, var w: IntRange) : Moveable {

    override fun canMove(direction: String): Boolean {
        w.forEach {
            val room = currentLevel.rooms[player.x][player.y][player.z][it]!!
            if ((!room.doors.contains(direction))
                || !room.lockCheck(hasLock = room.lock, direction = direction, platePlace = room.link)
                || !destinationNotNull(direction)
            ) {
                if (room.slits == null) {
                    return false
                } else if (!room.slits.contains(direction)) {
                    return false
                }
            }
        }
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
            x += 1
            player.x = player.x + 1
        }
        else if(direction == "south") {
            x = x - 1
            player.x = player.x - 1
        }
        else if(direction == "east") {
            y = y + 1
            player.y = player.y + 1
        }
        else if(direction == "west") {
            y = y - 1
            player.y = player.y - 1
        }
    }

    override fun failMove(direction: String): String {
        val myRoom = currentLevel.rooms[player.x][player.y][player.z][player.w]!!
        return if (
            direction != "north"
            && direction != "south"
            && direction != "east"
            && direction != "west"
        ) "I don't understand"
        else if (
            myRoom.doors.contains(direction)
            && myRoom.lockCheck(hasLock = myRoom.lock, direction = direction, platePlace = myRoom.link)
        ) "The tesseract stops at the doorway."
        else if (myRoom.doors.contains(direction)) "The door won't open."
        else "There's no door that way."
    }

    // currently unused, I think?
    override fun isInRoom(coordinates: Coordinates): Boolean {
        return  coordinates.x == x
                && coordinates.y == y
                && coordinates.z == z
                && w.contains(coordinates.w)
    }
}