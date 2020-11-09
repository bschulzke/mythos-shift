import dev.mythos.dsl.Room
import dev.mythos.dsl.game
import dev.mythos.dsl.room
import dev.mythos.game.runGame
import dev.mythos.dsl.Room
import dev.mythos.dsl.room

val level1 =
        Array(2) {
            Array(2) {
                Array(2) {
                    Array<Room?>(2) {
                        null
                    }
                }
            }
        }

var x = 1
var y = 1
var z = 1
var w = 1

var currentLevel = level1

fun mazeRoom(number: String, color: String, doors: List<String>, ladderDirection: String? = null, other: String? = null): Room<State> {
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
            if (doors.contains("east") && ladder.contains("up")) {
                middle += "  #  ║"
            }
            else if (doors.contains("east") && ladder.contains("down")) {
                middle += "  #  ║"
            }
            else if (doors.contains("east")) {
                middle += "     ║"
            }
            else if (ladder.contains("up") || ladder.contains("down"))
            {
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
            say("The number $number is marked in $color on the floor. $doors.$ladder $other")
        }
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (currentLevel[x+1][y][z][w] != null) {
                        x = x + 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
    }
                "south" -> {
                    if (currentLevel[x-1][y][z][w] != null) {
                        x = x - 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "east" -> {
                    if (currentLevel[x][y+1][z][w] != null) {
                        y = y + 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "west" -> {
                    if (currentLevel[x][y-1][z][w] != null) {
                        y = y - 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "up" -> {
                    if (currentLevel[x][y][z+1][w] != null) {
                        z = z + 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "down" -> {
                    if (currentLevel[x][y][z-1][w] != null) {
                        z = z - 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
}




val main = game {
    level1[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf("north", "east",), ladderDirection = "up")
    level1[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf("south", "east",), ladderDirection = "up")
    level1[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("north", "west",), ladderDirection = "up")
    level1[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("north", "east",), ladderDirection = "down")
    level1[0][0][0][1] = mazeRoom(number = "1.1.0", color = "blue", doors = listOf("north", "east",), ladderDirection = "up")
    level1[1][1][0][0] = mazeRoom(number = "1.1.0", color = "red", doors = listOf("south", "west"), ladderDirection = "up")
    level1[1][0][1][0] = mazeRoom(number = "1.0.1", color = "red", doors = listOf("south", "west"), ladderDirection = "down")
    level1[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south", "east"), ladderDirection = "up")
    level1[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf("south", "west"), ladderDirection = "down")
    level1[1][0][1][0] = mazeRoom(number = "1.0.1", color = "red", doors = listOf("south", "east"), ladderDirection = "up")
    level1[1][0][0][1] = mazeRoom(number = "", color = "", doors = listOf("", ""), ladderDirection = "")
}

fun main() {
    runGame(main)
}