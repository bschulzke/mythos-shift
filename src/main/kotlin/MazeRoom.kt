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
                say("Hint: The final room is marked marked 0.1.1 in blue.")
                hasStarted2 = true
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
                "up", "up the ladder", "up ladder" -> {
                    if (
                            playerPosition.z!! + 1 < currentLevel[playerPosition.x!!][playerPosition.y!!].size &&
                            currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!! + 1][playerPosition.w!!] != null &&
                            ladderDirection != null && ladderDirection.contains("up"))
                    {
                        playerPosition.z = playerPosition.z!! + 1
                        go(currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!!][playerPosition.w!!]!!)
                    } else {
                        say("There's no ladder that way.")
                    }
                }
                "down", "down the ladder", "down ladder" -> {
                    if (
                            playerPosition.z!! - 1 >= 0 &&
                            currentLevel[playerPosition.x!!][playerPosition.y!!][playerPosition.z!! - 1][playerPosition.w!!] != null &&
                            ladderDirection != null && ladderDirection.contains("down")) {
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