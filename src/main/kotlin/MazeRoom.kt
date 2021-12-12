import dev.mythos.dsl.Room
import dev.mythos.dsl.RoomContext
import dev.mythos.dsl.room

fun lockCheck(
        hasLock: String?,
        direction: String,
        platePlace: Coordinates?

): Boolean {
    return (hasLock == null) || (!direction.contains(hasLock) || boxes.contains(platePlace!!) || tesseracts.contains(platePlace))
}

data class MazeRoom(
        val doors: List<String>,
        val ladderDirection: String?,
        val lock: String?,
        val link: Coordinates?,
        val room: Room,
        val slits: List<String>?
) {
    fun lockCheck(
            hasLock: String?,
            direction: String,
            platePlace: Coordinates?

    ): Boolean {
        return (hasLock == null) || (!direction.contains(hasLock) || boxes.contains(platePlace!!) || tesseracts.contains(platePlace))
    }
}

fun mazeRoom(
    number: String,
    color: String,
    doors: List<String>,
    ladderDirection: String? = null,
    other: String? = null,
    isFinish: Boolean = false,
    flaps: List<String> = listOf(),

    hasPlate: Boolean = false,
    plateLetter: String? = null,
    lockLetter: String? = null,
    lock: String? = null,
    link: Coordinates? = null,

    roomBlock: (RoomContext.() -> Unit)? = null
): MazeRoom {

    val doorText = when (doors.size) {
        0 -> "There aren't any doors"
        1 -> "There's a door to the ${doors[0]}"
        else -> "There are doors to the ${doors.slice(0..(doors.size - 2)).joinToString(", ")} and ${doors.last()}"
    }

    val ladder = when (ladderDirection) {
        "up down" -> " There's a ladder going up and down."
        "up" -> " There's a ladder going up."
        "down" -> " There's a ladder going down."
        else -> ""
    }

    val flapText = when (flaps.size) {
        0 -> ""
        1 -> "There's a small opening to the ${flaps[0]}."
        else -> "There are small openings to the ${flaps.slice(0..(flaps.size - 2)).joinToString(", ")} and ${flaps.last()}."
    }

    val room = room {
        action("checkpoint", "Checkpoint") {
            say("Your checkpoint code is: ${getLevel()}${player.x}${player.y}${player.z}${player.w}")
            say("Copy or note this code down somewhere you won't lose it.")
            say("Next time you begin the game, type \"Go to checkpoint: \" followed by your code to return here.")
        }
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
                say("HINT: Your first CHECKPOINT in Level 5 is the room marked 2.2.0 in yellow.")
                hasStarted5 = true
            }
            val top = when {
                doorText.contains("north") -> "┌─═══─┐"
                flaps.contains("north") -> "┌─- -─┐"
                else -> "┌─────┐"
            }
            var middle = ""
            if (doorText.contains("west")) {
                middle += "║"
            } else if (flaps.contains("west")) {
                middle += "¦"
            } else {
                middle += "│"
            }
            if (isFinish && doorText.contains("east")) {
                middle += "  *  ║"
            } else if (isFinish && flaps.contains("east")) {
                middle += "  *  ¦"
            }
            else if (isFinish) {
                middle += "  *  │"
            }
            else if (hasPlate && doorText.contains("east") && boxes.contains(player)) {
                middle += " [▆] ║"
            } else if (hasPlate && flaps.contains("east") && boxes.contains(player)) {
                middle += " [▆] ¦"
            }
            else if (hasPlate && boxes.contains(player)) {
                middle += " [▆] │"
            }
            else if (hasPlate && doorText.contains("east") && tesseracts.contains(player)) {
                middle += " [T] ║"
            }
            else if (hasPlate && flaps.contains("east") && tesseracts.contains(player)) {
                middle += " [T] ¦"
            }
            else if (hasPlate && tesseracts.contains(player)) {
                middle += " [T] │"
            }
            else if (hasPlate && doorText.contains("east")) {
                middle += "  ░  ║"
            }
            else if (hasPlate && flaps.contains("east")) {
                middle += "  ░  ¦"
            }
            else if (hasPlate) {
                middle += "  ░  │"
            }
            else if (
                    doorText.contains("east") &&
                            ladderDirection != null &&
                            boxes.contains(player)
                    ) {
                middle += " # ▆ ║"
                boxes.contains(player)
            }
            else if (
                flaps.contains("east") &&
                ladderDirection != null &&
                boxes.contains(player)
            ) {
                middle += " # ▆ ¦"
                boxes.contains(player)
            }
            else if (
                    ladderDirection != null &&
                    boxes.contains(player)
            ) {
                middle += " # ▆ │"
            }
            else if (
                    doorText.contains("east") &&
                    boxes.contains(player)
            ) {
                middle += "  ▆  ║"
            }
            else if (
                flaps.contains("east") &&
                boxes.contains(player)
            ) {
                middle += "  ▆  ¦"
            }
            else if (
                    boxes.contains(player)
            ) {
                middle += "  ▆  │"
            }
            // tesseracts
            else if (
                doorText.contains("east") &&
                ladderDirection != null &&
                (tesseracts.contains(player))
            ) {
                middle += " # T ║"
                (tesseracts.contains(player))
            }
            else if (
                flaps.contains("east") &&
                ladderDirection != null &&
                (tesseracts.contains(player))
            ) {
                middle += " # T ¦"
                (tesseracts.contains(player))
            }
            else if (
                ladderDirection != null &&
                (tesseracts.contains(player))
            ) {
                middle += " # T │"
            }
            else if (
                doorText.contains("east") &&
                (tesseracts.contains(player))
            ) {
                middle += "  T  ║"
            }
            else if (
                flaps.contains("east") &&
                (tesseracts.contains(player))
            ) {
                middle += "  T  ¦"
            }
            else if (
                (tesseracts.contains(player))
            ) {
                middle += "  T  │"
            }
            else if (doorText.contains("east") && ladderDirection != null) {
                middle += "  #  ║"
            } else if (flaps.contains("east") && ladderDirection != null) {
                middle += "  #  ¦"
            } else if (doorText.contains("east")) {
                middle += "     ║"
            }
            else if (flaps.contains("east")) {
                middle += "     ¦"
            }
            else if (ladderDirection != null) {
                middle += "  #  │"
            } else {
                middle += "     │"
            }
            val bottom = when {
                doorText.contains("south") -> "└─═══─┘"
                flaps.contains("south") -> "└─- -─┘"
                else -> "└─────┘"
            }
            if (color == "yellow") {
                say("""
                    <span style="color:gold">
                        ${top}
                        ${middle}
                        ${bottom}
                    </span>
                    """.trimIndent())
            }
            else {
                say("""
                    <span style="color:$color">
                        ${top}
                        ${middle}
                        ${bottom}
                    </span>
                    """.trimIndent())
            }
            say("The number $number is marked in $color on the floor. $doorText.$ladder")
            if (other != null) {
                say("$other")
            }
            if (lockText != null) {
                say("$lockText")
            }
            if (hasPlate && boxes.contains(player)) {
                say("There's a box on the pressure plate in this room, which is marked with a $plateLetter.")
            } else if (hasPlate && tesseracts.contains(player)) {
                say("There's a tesseract on the pressure plate in this room, which is marked with a $plateLetter")
            }
            else if (hasPlate) {
                say("There's a pressure plate with a $plateLetter on it.")
            }
            if (flapText != "") {
                say(flapText)
                if (!flapExplianed) {
                    say(flapExplination)
                    flapExplianed = true
                }
            }
        }

        action("go to level two") {
            currentLevel = level2
            player.x = 0
            player.y = 0
            player.z = 0
            player.w = 0
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (
                            player.x + 1 < currentLevel.rooms.size &&
                            currentLevel.rooms[player.x + 1][player.y][player.z][player.w] != null &&
                            doorText.contains("north") && lockCheck(hasLock = lock, direction = direction, platePlace = link))
                    { player.x = player.x + 1
                        go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
                    } else if (doorText.contains("north")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "south" -> {
                    if (
                            player.x - 1 >= 0 &&
                            currentLevel.rooms[player.x - 1][player.y][player.z][player.w] != null
                            && doorText.contains("south") && lockCheck(hasLock = lock, direction = direction, platePlace = link)) {
                        player.x = player.x - 1
                        go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
                    } else if (doorText.contains("south")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "east" -> {
                    if (
                            player.y + 1 < currentLevel.rooms[player.x].size &&
                            currentLevel.rooms[player.x][player.y + 1][player.z][player.w] != null &&
                            doorText.contains("east") && lockCheck(hasLock = lock, direction = direction, platePlace = link))
                    {
                        player.y = player.y + 1
                        go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
                    } else if (doorText.contains("east")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "west" -> {
                    if (
                            player.y - 1 >= 0 &&
                            currentLevel.rooms[player.x][player.y - 1][player.z][player.w] != null &&
                            doorText.contains("west") && lockCheck(hasLock = lock, direction = direction, platePlace = link)) {
                        player.y = player.y - 1
                        go(currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!!]!!.room)
                    } else if (doorText.contains("west")) {
                        say("The door won't open.")
                    } else {
                        say("There's no door that way.")
                    }
                }
                "up", "up the ladder", "up ladder" -> {
                    if (
                            player.z!! + 1 < currentLevel.rooms[player.x!!][player.y!!].size &&
                            currentLevel.rooms[player.x!!][player.y!!][player.z!! + 1][player.w!!] != null &&
                            ladderDirection != null && ladderDirection.contains("up") &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)
                    )
                    {
                        player.z = player.z!! + 1
                        go(currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!!]!!.room)
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
                            currentLevel.rooms[player.x!!][player.y!!][player.z!! - 1][player.w!!] != null &&
                            ladderDirection != null && ladderDirection.contains("down") &&
                            lockCheck(hasLock = lock, direction = direction, platePlace = link)
                    ) {
                        player.z = player.z!! - 1
                        go(currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!!]!!.room)
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
                            player.w!! + 1 < currentLevel.rooms[player.x!!][player.y!!][player.z!!].size &&
                            currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!! + 1] != null)
                    {
                        player.w = player.w!! + 1
                        go(currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!!]!!.room)
                    }
                    else {
                        say("There's nothing in that direction.")
                    }
                }
                "ana" -> {
                    if (
                            player.w!! - 1 >= 0 &&
                            currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!! - 1] != null)
                    {
                        player.w = player.w!! - 1
                        go(currentLevel.rooms[player.x!!][player.y!!][player.z!!][player.w!!]!!.room)
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
        action("show map") {
            if (currentLevel == level1) {
                say("\"<a href='map01.png' target='_blank'>Click here</a>\"")
            }
            else if (currentLevel == level2) {
                say("\"<a href='map02.png' target='_blank'>Click here</a>\"")
            }
            else if (currentLevel == level3) {
                say("\"<a href='map03.png' target='_blank'>Click here</a>\"")
            }
            else if (currentLevel == level4) {
                say("\"<a href='map04.png' target='_blank'>Click here</a>\"")
            }
            else if (currentLevel == level5) {
                say("\"<a href='map05.png' target='_blank'>Click here</a>\"")
            }
        }
        action("push box (.*)", "push it (.*)", "push (.*)", "push the box (.*)", "push tesseract (.*)", "push the tesseract (.*)") {(direction) ->
             if (boxes.contains(player)) {
                val box = boxes.find(player)
                if (box!!.canMove(direction)) {
                    box.move(direction)
                    go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
                } else {
                    val failMove = box.failMove(direction)
                    say(failMove)
                }
            } else if (tesseracts.contains(player)) {
                val tesseract = tesseracts.find(player)
                if (tesseract!!.canMove(direction)) {
                    tesseract.move(direction)
                    go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
                } else {
                    val failMove = tesseract.failMove(direction)
                    say(failMove)
                }
            }
            else {
                say("There's no box or tesseract in this room.")
            }
        }
        if (roomBlock != null) {
            roomBlock()
        }
    }

    return MazeRoom(
            doors,
            ladderDirection,
            lock,
            link,
            room,
            flaps
    )
}