import dev.mythos.dsl.Room
import dev.mythos.dsl.game
import dev.mythos.dsl.room
import dev.mythos.game.runGame

val level1 =

        Array(1) {
            Array(2) {
                Array(2) {
                    Array<Room?>(2) {
                        null
                    }
                }
            }
        }

val level2 =
        Array(2) {
            Array(2) {
                Array(2) {
                    Array<Room?>(2) {
                        null
                    }
                }
            }
        }

var x = 0
var y = 1
var z = 0
var w = 0

var currentLevel = level1

fun mazeRoom(number: String, color: String, doors: List<String>, ladderDirection: String? = null, other: String? = null): Room {
    val doors = when {
        doors.size == 0 -> "There aren't any doors"
        doors.size == 1 -> "There's a door to the ${doors[0]}"
        else -> "There are doors to the ${doors.slice(0..(doors.size - 2)).joinToString(", ")} and ${doors.last()}"
    }
    val ladder = when {
        ladderDirection == "up" -> " There's a ladder going up."
        ladderDirection == "down" -> " There's a ladder going down."
        else -> ""
    }
    val other = when {
        other == null -> ""
        else -> "\n$other"
    }
    return room() {
        onEnter {
            val top = when {
                doors.contains("north") -> "┌─═══─┐"
                else -> "┌─────┐"
            }
            var middle = ""
            if (doors.contains("west")) {
                middle += "║"
            } else {
                middle += "│"
            }
            if (other.contains("finish") && doors.contains("east")) {
            middle += "  *  ║"
        }
        else if (other.contains("finish")) {
            middle += "  *  │"
        }
            else if (doors.contains("east") && ladder.contains("up")) {
                middle += "  #  ║"
            } else if (doors.contains("east") && ladder.contains("down")) {
                middle += "  #  ║"
            } else if (doors.contains("east")) {
                middle += "     ║"
            } else if (ladder.contains("up") || ladder.contains("down")) {
                middle += "  #  │"
            }
            else {
                middle += "     │"
            }
            val bottom = when {
                doors.contains("south") -> "└─═══─┘"
                else -> "└─────┘"
            }
            say("""
                        ${top}
                        ${middle}
                        ${bottom}
                    """.trimIndent())
            say("The number $number is marked in $color on the floor. $doors.$ladder")
            if (other.contains("finish")) {
                say("You finished the level! Would you like to go on? If so, say, 'go on.'")
            }
            else if (other != null) {
                say("$other")
            }
        }
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (x + 1 < currentLevel.size && currentLevel[x + 1][y][z][w] != null && doors.contains("north")) {
                        x = x + 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else if (doors.contains("north")) {
                        say("The door won't open.")
                    }
                    else {
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (x - 1 >= 0 && currentLevel[x - 1][y][z][w] != null && doors.contains("south")) {
                        x = x - 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else if (doors.contains("north")) {
                        say("The door won't open.")
                    }
                    else {
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (y + 1 < currentLevel[x].size && currentLevel[x][y + 1][z][w] != null && doors.contains("east")) {
                        y = y + 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else if (doors.contains("north")) {
                        say("The door won't open.")
                    }
                    else {
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (y - 1 >= 0 && currentLevel[x][y - 1][z][w] != null && doors.contains("west")) {
                        y = y - 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else if (doors.contains("north")) {
                        say("The door won't open.")
                    }
                    else {
                        say("There's no door that way.")
                    }
                }
                "up" -> {
                    if (z + 1 < currentLevel[x][y].size && currentLevel[x][y][z + 1][w] != null && ladderDirection!!.contains("up")) {
                        z = z + 1
                        go(currentLevel[x][y][z][w]!!)
                    } else {
                        say("There's no ladder.")
                    }
                }
                "down" -> {
                    if (z - 1 >= 0 && currentLevel[x][y][z - 1][w] != null && ladderDirection!!.contains("down")) {
                        z = z - 1
                        go(currentLevel[x][y][z][w]!!)
                    } else {
                        say("There's no ladder.")
                    }
                }
            }
        }
        action("shift (.*)") {(direction) ->
            when (direction) {
                "kata" -> {
                    if (w + 1 < currentLevel[x][y][z].size && currentLevel[x][y][z][w + 1] != null) {
                        w = w + 1
                        go(currentLevel[x][y][z][w]!!)
                    } else {
                        say("There's nothing in that direction.")
                    }
                }
                "ana" -> {
                    if(w - 1 >= 0 && currentLevel[x][y][z][w - 1] != null) {
                        w = w - 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else{
                        say("There's nothing in that direction.")
                    }
                }
            }
        }
        action("go on") {
            if (other.contains("finish") && currentLevel == level1) {
                currentLevel = level2
                go(level2[0][0][0][0]!!)
            }
            else {
                say("I don't understand.")
            }
        }
    }
}


val main = game {
    //region level1 rooms
    level1[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf("east"), ladderDirection = "up", other = "finish")
    level1[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf(), ladderDirection = "up")

    level1[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("east"), ladderDirection = "")
    level1[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf("west"), ladderDirection = "down")

    level1[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("east"), ladderDirection = "up")
    level1[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf(), ladderDirection = "")

    level1[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("east"), ladderDirection = "down")
    level1[0][1][1][1] = mazeRoom(number = "0.1.1", color = "blue", doors = listOf("west"), ladderDirection = "down")
    //endregion

    //region level2 rooms
    level2[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf("north", "east", ), ladderDirection = "up",)
    level2[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("north", "west", ), ladderDirection = "up")
    level2[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf("south", "east", ), ladderDirection = "up")
    level2[1][1][0][0] = mazeRoom(number = "1.1.0", color = "red", doors = listOf("south", "west"), ladderDirection = "up")

    level2[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("north", "east", ), ladderDirection = "down")
    level2[0][1][1][0] = mazeRoom(number= "0.1.1", color = "red", doors = listOf("north", "west",), ladderDirection = "down")
    level2[1][0][1][0] = mazeRoom(number = "1.0.1", color = "red", doors = listOf("south", "east"), ladderDirection = "down")
    level2[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf("south", "west"), ladderDirection = "down")

    level2[0][0][0][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("north", "east", ), ladderDirection = "up")
    level2[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf("north", "west", ), ladderDirection = "up")
    level2[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south", "east"), ladderDirection = "up")
    level2[1][1][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south", "west"), ladderDirection = "up")

    level2[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("south", "west"), ladderDirection = "down")
    level2[0][1][1][1] = mazeRoom(number = "0.1.1", color = "blue", doors = listOf("north", "west"), ladderDirection = "down")
    level2[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("north", "east"), ladderDirection = "down")
    level2[1][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("south", "east",), ladderDirection = "down")
    //endregion

    initialRoom = level1[0][1][0][0]!!
}

fun main() {
    runGame(main)
}
