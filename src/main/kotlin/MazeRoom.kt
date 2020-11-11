import dev.mythos.dsl.ActionsContext
import dev.mythos.dsl.Room
import dev.mythos.dsl.RoomContext
import dev.mythos.dsl.room

fun mazeRoom(
        number: String,
        color: String,
        doors: List<String>,
        ladderDirection: String? = null,
        other: String? = null,
        isFinish: Boolean = false,

        roomBlock: (RoomContext.() -> Unit)? = null
): Room {
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
    return room() {
        onEnter {
            if (!hasStarted) {
                say("Welcome to Shift! Please read these instructions carefully!")
                say("In this game you will be presented with a series of mazes.")
                say("The room you're currently in will always be shown by the most recent box, which will look something like this:")
                say("""
                    ┌─────┐
                    │     │
                    └─────┘
                    """.trimIndent())
                say("To navigate these mazes, you can type things like 'go north', or 'go east,' to move through doors, if there are any.")
                say("If there's a ladder, you can also say 'go up' or 'go down.'")
                say("Remember when you're looking at the screen, north is always up, south is down, east is right, and west is left.")
                say("There's reminder text below each box that tells you which walls have doors, and provides additional info.")
                say("But if you ever get confused about directions, you can say, 'show compass,' and this will appear on your screen:")
                say("""
                       ▲
                       N
                   ◄W    E►
                       S
                       ▼
                       
                    """.trimIndent())
                say("Doors are represented by this symbol: ║ for east or west.")
                say("And by this symbol: ═══ for north and south.")
                say("Ladders, both up and down, are represented by this: #")
                say("Refer to the text below the map to know whether a ladder goes up or down, and any other info about the room.")
                say("")
                say("In addition to moving in the three dimensions we're familiar with via ladders and doors, you can also at any moment shift ana or kata.")
                say("This is a special power you can do anywhere, as long as there's a room in that direction, which you'll only find out by trying.")
                say("Remember, ana and kata are directions along a third axis, so it's not up, down, north, south, east or west.")
                say("They're two entirely different spacial directions.")
                say("To move ana or kata, say, 'shift ana' or 'shift kata.'")
                say("Your goal in every maze will always be to find the room with this symbol in the middle: *")
                say("Good luck!")
                hasStarted = true
            }
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
            if (isFinish && doors.contains("east")) {
                middle += "  *  ║"
            } else if (isFinish) {
                middle += "  *  │"
            } else if (doors.contains("east") && ladder.contains("up")) {
                middle += "  #  ║"
            } else if (doors.contains("east") && ladder.contains("down")) {
                middle += "  #  ║"
            } else if (doors.contains("east")) {
                middle += "     ║"
            } else if (ladder.contains("up") || ladder.contains("down")) {
                middle += "  #  │"
            } else {
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
            if (other != null) {
                say("$other")
            }
        }
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (x + 1 < currentLevel.size && currentLevel[x + 1][y][z][w] != null && doors.contains("north")) {
                        x = x + 1
                        go(currentLevel[x][y][z][w]!!)
                    } else if (doors.contains("north")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (x - 1 >= 0 && currentLevel[x - 1][y][z][w] != null && doors.contains("south")) {
                        x = x - 1
                        go(currentLevel[x][y][z][w]!!)
                    } else if (doors.contains("north")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (y + 1 < currentLevel[x].size && currentLevel[x][y + 1][z][w] != null && doors.contains("east")) {
                        y = y + 1
                        go(currentLevel[x][y][z][w]!!)
                    } else if (doors.contains("north")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (y - 1 >= 0 && currentLevel[x][y - 1][z][w] != null && doors.contains("west")) {
                        y = y - 1
                        go(currentLevel[x][y][z][w]!!)
                    } else if (doors.contains("north")) {
                        say("The door won't open.")
                    } else {
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
        action("shift (.*)") { (direction) ->
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
                    if (w - 1 >= 0 && currentLevel[x][y][z][w - 1] != null) {
                        w = w - 1
                        go(currentLevel[x][y][z][w]!!)
                    } else {
                        say("There's nothing in that direction.")
                    }
                }
            }
        }
        action("show compass") {
            say("""
                       ▲
                       N
                   ◄W    E►
                       S
                       ▼
                       
                    """.trimIndent())
        }
        if (roomBlock != null) {
            roomBlock()
        }
    }
}