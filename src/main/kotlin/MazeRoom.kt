import dev.mythos.dsl.Room
import dev.mythos.dsl.RoomContext
import dev.mythos.dsl.room

fun lockCheck(
        hasLock: String?,
        direction: String,
        platePlace: Coordinates?

): Boolean {
    return hasLock == null || !direction.contains(hasLock) || boxes.contains(platePlace)
}

fun mazeRoom(
        number: String,
        color: String,
        doors: List<String>,
        ladderDirection: String? = null,
        other: String? = null,
        isFinish: Boolean = false,

        hasPlate: Boolean = false,
        plateLetter: String? = null,
        lockLetter: String? = null,
        lock: String? = null,
        link: Coordinates? = null,

        roomBlock: (RoomContext.() -> Unit)? = null
): Room {

    val doors = when {
        doors.size == 0 -> "There aren't any doors"
        doors.size == 1 -> "There's a door to the ${doors[0]}"
        else -> "There are doors to the ${doors.slice(0..(doors.size - 2)).joinToString(", ")} and ${doors.last()}"
    }
    val ladder = when {
        ladderDirection == "up down" -> " There's a ladder going up and down."
        ladderDirection == "up" -> " There's a ladder going up."
        ladderDirection == "down" -> " There's a ladder going down."
        else -> ""
    }

    return room() {
        onEnter {
            val lockText = when {
                lock != null && (lock.contains("up") || lock.contains("down")) &&
                        link != null &&
                        link.w == 0 -> "There's a red $lockLetter by the ladder."

                lock != null && (lock.contains("up") || lock.contains("down")) &&
                        link != null
                        && link.w == 1 -> "There's a blue $lockLetter by the ladder."
                lock != null && (lock.contains("up") || lock.contains("down")) &&
                        link != null &&
                        link.w == 2 -> "There's a green $lockLetter by the ladder."
                lock != null && (lock.contains("up") || lock.contains("down")) &&
                        link != null &&
                        link.w == 3 -> "There's a yellow $lockLetter by the ladder."
                lock != null && (lock.contains("up") || lock.contains("down")) &&
                        link != null &&
                        link.w == 4 -> "There's a orange $lockLetter by the ladder."
                lock != null && (lock.contains("up") || lock.contains("down")) &&
                        link != null &&
                        link.w == 5 -> "There's a purple $lockLetter by the ladder."

                lock != null && link != null && link.w == 0 -> "There's a red $lockLetter over the $lock door."
                lock != null && link != null && link.w == 1 -> "There's a blue $lockLetter over the $lock door."
                lock != null && link != null && link.w == 2 -> "There's a green $lockLetter over the $lock door."
                lock != null && link != null && link.w == 3 -> "There's a yellow $lockLetter over the $lock door."
                lock != null && link != null && link.w == 4 -> "There's a orange $lockLetter over the $lock door."
                lock != null && link != null && link.w == 5 -> "There's a purple $lockLetter over the $lock door."
                else -> null
            }
            if (!hasStarted2 && hasStarted1 && currentLevel == level2) {
                say("")
                say("WELCOME TO LEVEL 2!")
                say("HINT: The final room is marked marked 0.1.1 in blue.")
                hasStarted2 = true
            }
            if (!hasStarted3 && currentLevel == level3) {
                say("")
                say("WELCOME TO LEVEL 3!")
                say("HINT: The final room is marked marked 1.0.0 in green.")
                hasStarted3 = true
            }
            if (!hasStarted4 && currentLevel == level4) {
                say("")
                say("WELCOME TO LEVEL 4!")
                say("HINT: The final room is marked marked 2.0 in green.")
                hasStarted4 = true
            }
            if (!hasStarted5 && currentLevel == level5) {
                say("")
                say("WELCOME TO LEVEL 5!")
                say("HINT: Working on hints")
                hasStarted5 = true
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
            else if (hasPlate && doors.contains("east") && boxes.contains(player)) {
                middle += " [▆] ║"
            }
            else if (hasPlate && boxes.contains(player)) {
                middle += " [▆] │"
            }
            else if (hasPlate && doors.contains("east")) {
                middle += "  ░  ║"
            }
            else if (hasPlate) {
                middle += "  ░  │"
            }
            else if (
                    doors.contains("east") &&
                            ladderDirection != null &&
                    boxes.contains(player)
                    ) {
                middle += " # ▆ ║"
                boxes.contains(player)
            }
            else if (
                    ladderDirection != null &&
                    boxes.contains(player)
            ) {
                middle += " # ▆ │"
            }
            else if (
                    doors.contains("east") &&
                    boxes.contains(player)
            ) {
                middle += "  ▆  ║"
            }
            else if (
                    boxes.contains(player)
            ) {
                middle += "  ▆  │"
            }
            else if (doors.contains("east") && ladderDirection != null) {
                middle += "  #  ║"
            }  else if (doors.contains("east")) {
                middle += "     ║"
            } else if (ladderDirection != null) {
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
            if (lockText != null) {
                say("$lockText")
            }
            if (hasPlate && boxes.contains(player)) {
                say("There's a box on the pressure plate in this room, which is marked with a $plateLetter.")
            }
            else if (hasPlate) {
                say("There's a pressure plate with a $plateLetter on it.")
            }
        }

        action("go to level two") {
            currentLevel = level2
            player.x = 0
            player.y = 0
            player.z = 0
            player.w = 0
            go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
        }
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (
                            player.x!! + 1 < currentLevel.size &&
                            currentLevel[player.x!! + 1][player.y!!][player.z!!][player.w!!] != null &&
                            doors.contains("north") && lockCheck(hasLock = lock, direction = direction, platePlace = link))
                    { player.x = player.x!! + 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    } else if (doors.contains("north")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (
                            player.x!! - 1 >= 0 &&
                            currentLevel[player.x!! - 1][player.y!!][player.z!!][player.w!!] != null
                            && doors.contains("south") && lockCheck(hasLock = lock, direction = direction, platePlace = link)) {
                        player.x = player.x!! - 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    } else if (doors.contains("south")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (
                            player.y!! + 1 < currentLevel[player.x!!].size &&
                            currentLevel[player.x!!][player.y!! + 1][player.z!!][player.w!!] != null &&
                            doors.contains("east") && lockCheck(hasLock = lock, direction = direction, platePlace = link))
                    {
                        player.y = player.y!! + 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    } else if (doors.contains("east")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (
                            player.y!! - 1 >= 0 &&
                            currentLevel[player.x!!][player.y!! - 1][player.z!!][player.w!!] != null &&
                            doors.contains("west") && lockCheck(hasLock = lock, direction = direction, platePlace = link)) {
                        player.y = player.y!! - 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    } else if (doors.contains("west")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "up", "up the ladder", "up ladder" -> {
                    if (
                            player.z!! + 1 < currentLevel[player.x!!][player.y!!].size &&
                            currentLevel[player.x!!][player.y!!][player.z!! + 1][player.w!!] != null &&
                            ladderDirection != null && ladderDirection.contains("up") &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)
                    )
                    {
                        player.z = player.z!! + 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    }
                    else if (
                            ladderDirection != null && ladderDirection.contains("up")
                            )
                    {
                        say("There's a trapdoor blocking this ladder.")
                    }
                    else {
                        say("There's no ladder that way.")
                    }
                }
                "down", "down the ladder", "down ladder" -> {
                    if (
                            player.z!! - 1 >= 0 &&
                            currentLevel[player.x!!][player.y!!][player.z!! - 1][player.w!!] != null &&
                            ladderDirection != null && ladderDirection.contains("down") &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)
                    ) {
                        player.z = player.z!! - 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    }
                    else if (
                            ladderDirection != null && ladderDirection.contains("down")
                    )
                    {
                        say("There's a trapdoor blocking this ladder.")
                    }
                    else {
                        say("There's no ladder that way.")
                    }
                }
            }
        }
        action("shift (.*)") { (direction) ->
            when (direction) {
                "kata" -> {
                    if (
                            player.w!! + 1 < currentLevel[player.x!!][player.y!!][player.z!!].size &&
                            currentLevel[player.x!!][player.y!!][player.z!!][player.w!! + 1] != null)
                    {
                        player.w = player.w!! + 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "ana" -> {
                    if (
                            player.w!! - 1 >= 0 &&
                            currentLevel[player.x!!][player.y!!][player.z!!][player.w!! - 1] != null)
                    {
                        player.w = player.w!! - 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
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
        action("push box (.*)", "push it (.*)", "push the box (.*)") {(direction) ->
            when (direction) {
                "north" -> {
                    if (
                            player.x!! + 1 < currentLevel.size &&
                            currentLevel[player.x!! + 1][player.y!!][player.z!!][player.w!!] != null &&
                            doors.contains("north") &&
                            boxes.contains(player) &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)

                    ) {
                        boxes.find {it == player}!!.x = player.x!! + 1
                        player.x = player.x!! + 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    }
                    else if (doors.contains("north") &&
                            boxes.contains(player)
                    ) {
                        say("The door won't open.")
                    }
                    else if (boxes.contains(player)){
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (
                            player.x!! - 1 >= 0 &&
                            currentLevel[player.x!! - 1][player.y!!][player.z!!][player.w!!] != null &&
                            doors.contains("south") &&
                            boxes.contains(player) &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)

                    ) {
                        boxes.find {it == player}!!.x = player.x!! - 1
                        player.x = player.x!! - 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    }
                    else if (doors.contains("south") &&
                            boxes.contains(player)
                    ) {
                        say("The door won't open.")
                    }
                    else if (boxes.contains(player)){
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (
                            player.y!! + 1 < currentLevel[player.x!!].size &&
                            currentLevel[player.x!!][player.y!! + 1][player.z!!][player.w!!] != null
                            && doors.contains("east") &&
                            boxes.contains(player) &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)

                    ) {
                        boxes.find {it == player}!!.y = player.y!! + 1
                        player.y = player.y!! + 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    } else if (
                            doors.contains("east") &&
                            boxes.contains(player)) {
                        say("The door won't open.")
                    } else if (boxes.contains(player)){
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (
                            player.y!! - 1 >= 0 &&
                            currentLevel[player.x!!][player.y!! - 1][player.z!!][player.w!!] != null &&
                            doors.contains("west") &&
                            boxes.contains(player) &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)

                    ) {
                        boxes.find {it == player}!!.y = player.y!! - 1
                        player.y = player.y!! - 1
                        go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
                    }
                    else if (doors.contains("west" )&&
                            boxes.contains(player)
                    ) {
                        say("The door won't open.")}
                    else if (boxes.contains(player)){
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