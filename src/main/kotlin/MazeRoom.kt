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
            if (!hasStarted2 && hasStarted1 && currentLevel == level2) {
                say("")
                say("WELCOME TO LEVEL 2!")
                say("Hint: The final room is marked marked 1.1.1 in blue.")
                hasStarted2 = true
            }
            if (!hasStarted1) {
                say("Welcome to Shift! Please read these instructions carefully!")
                say("In this game you will be presented with a series of mazes.")
                say("The room you're currently in will always be shown by the most recent box, " +
                        "which will look something like this:")
                say("""
                    ┌─────┐
                    │     │
                    └─────┘
                    """.trimIndent())
                say("Doors are represented by this symbol: ║ for east or west.")
                say("And by this symbol: ═══ for north and south.")
                say("Ladders, both up and down, are represented by this: #")
                say("Refer to the text below the map to know whether a ladder goes up or down, " +
                        "and any other info about the room.")
                say("To navigate these mazes, you can type things like 'go north', or 'go east,' to move through doors, " +
                        "if there's a door in that direction.")
                say("If there's a ladder, you can also say 'go up' or 'go down.'")
                say("")
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
                say("In addition to moving in the three dimensions we're familiar with via ladders and doors, " +
                        "you can also at any moment shift ana or kata.")
                say("This is a special power you can do anywhere, as long as there's a room in that direction, " +
                        "which you'll only find out by trying.")
                say("Remember, ana and kata are directions along a third axis, so it's not up, down, north, south, east or west.")
                say("They're two entirely different spacial directions.")
                say("To move ana or kata, say, 'shift ana' or 'shift kata.'")
                say("Your goal in every maze will always be to find the room with this symbol in the middle: *")
                say("Good luck!")
                say("")
                say("WELCOME TO LEVEL 1!")
                say("HINT: You're looking for the room with 0.0.0 marked in red.")
                hasStarted1 = true
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
            }
            else if (
                    doors.contains("east") &&
                            ladderDirection != null &&
                    currentLevel[a][b][c][d] ==
                    currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                middle += " # ▆ ║"
            }
            else if (
                    ladderDirection != null &&
                    currentLevel[a][b][c][d] ==
                    currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
            ) {
                middle += " # ▆ │"
            }
            else if (
                    doors.contains("east") &&
                    currentLevel[a][b][c][d] ==
                    currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
            ) {
                middle += "  ▆  ║"
            }
            else if (
                    currentLevel[a][b][c][d] ==
                    currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
            ) {
                middle += "  ▆  │"
            }
            else if (doors.contains("east") && ladder.contains("up")) {
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
        action("go to level two") {
            currentLevel = level2
            playerPosition.x = 0
            playerPosition.y = 0
            playerPosition.z = 0
            playerPosition.w = 0
            go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
        }
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (
                            playerPosition.x!! + 1 < currentLevel.size &&
                            currentLevel[playerPosition.x!! + 1][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!] != null &&
                            doors.contains("north"))
                    { playerPosition.x = playerPosition.x!! + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else if (doors.contains("north")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (
                            playerPosition.x!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!! - 1][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!] != null
                            && doors.contains("south")) {
                        playerPosition.x = playerPosition.x!! - 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else if (doors.contains("south")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (
                            playerPosition.y!! + 1 < currentLevel[playerPosition.x!!].size &&
                            currentLevel[playerPosition.x!!][playerPosition.y!! + 1][playerPosition.z!!][playerPosition.w!!] != null &&
                            doors.contains("east"))
                    {
                        playerPosition.y = playerPosition.y!! + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else if (doors.contains("east")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (
                            playerPosition.y!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!!][playerPosition.y!! - 1][playerPosition.z!!][playerPosition.w!!] != null &&
                            doors.contains("west")) {
                        playerPosition.y = playerPosition.y!! - 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else if (doors.contains("west")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "up" -> {
                    if (
                            playerPosition.z!! + 1 < currentLevel.size &&
                            currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!! + 1][playerPosition.w!!] != null &&
                            doors.contains("north"))
                    { playerPosition.z = playerPosition.z!! + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else if (ladderDirection != null && ladderDirection.contains("up")) {
                        say("There's a locked trapdoor at the top of the ladder.")
                    } else {
                        say("There's no ladder going that way.")
                    }
                }
                "down" -> {
                    if (
                            playerPosition.z!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!! - 1][playerPosition.w!!] != null &&
                            ladderDirection!!.contains("down")) {
                        playerPosition.z = playerPosition.z!! - 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else {
                        say("There's no ladder that way.")
                    }
                }
            }
        }
        action("shift (.*)") { (direction) ->
            when (direction) {
                "kata" -> {
                    if (
                            playerPosition.w!! + 1 < currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!].size &&
                            currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!! + 1] != null)
                    {
                        playerPosition.w = playerPosition.w!! + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "ana" -> {
                    if (
                            playerPosition.w!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!! - 1] != null)
                    {
                        playerPosition.w = playerPosition.w!! - 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
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
        action("push box (.*)") {(direction) ->
            when (direction) {
                "north" -> {
                    if (
                            playerPosition.x!! + 1 < currentLevel.size &&
                            currentLevel[playerPosition.x!! + 1][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!] != null &&
                            doors.contains("north")
                            && currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        playerPosition.x = playerPosition.x!! + 1
                        a = a + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    }
                    else if (doors.contains("north") &&
                            currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        say("The door won't open.")
                    }
                    else {
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (
                            playerPosition.x!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!! - 1][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!] != null &&
                            doors.contains("south")
                            && currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        playerPosition.x = playerPosition.x!! - 1
                        a = a - 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    }
                    else if (doors.contains("south") &&
                            currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        say("The door won't open.")
                    }
                    else {
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (
                            playerPosition.y!! + 1 < currentLevel[playerPosition.x!!].size &&
                            currentLevel[playerPosition.x!!][playerPosition.y!! + 1][playerPosition.z!!][playerPosition.w!!] != null
                            && doors.contains("east")
                            && currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        playerPosition.y = playerPosition.y!! + 1
                        b = b + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else if (
                            doors.contains("east") &&
                            currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (
                            playerPosition.y!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!!][playerPosition.y!! - 1][playerPosition.z!!][playerPosition.w!!] != null &&
                            doors.contains("west") &&
                            currentLevel[a][b][c][d] == currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        playerPosition.y = playerPosition.y!! - 1
                        b = b - 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    }
                    else if (doors.contains("west" )&&
                            currentLevel[a][b][c][d] ==
                            currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]
                    ) {
                        say("The door won't open.")}
                    else if (currentLevel[a][b][c][d] == currentLevel [playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]){
                        say("There's no door that way.")
                    }
                }
            }
        }
        if (roomBlock != null) {
            roomBlock()
        }
    }
}