import dev.mythos.dsl.game
import dev.mythos.dsl.room
import dev.mythos.game.runGame
import kotlinx.browser.window

data class Coordinates(
        var x: Int,
        var y: Int,
        var z: Int,
        var w: Int,
)

var player = Coordinates (x = 0, y = 1, z = 0, w = 0)

var boxes = Moveables(listOf())

var tesseracts = Moveables(listOf())

//region room arrays
val level0 = Level(2, 2, 2, 2, 0,
    Coordinates(0,1,1, 1),
    tesseracts = Moveables(
        listOf(
            Tesseract(1, 1, 0, 0..2),
            Tesseract(0, 0, 1, 1..2)
        )
    )
)

val level1 = Level(1, 2, 2, 2, 1, Coordinates(x = 0, y = 1, z = 0, w = 0))

val level2 = Level(2, 2, 2, 2, 2, Coordinates(0, 0, 0, 0,))

val level3 = Level(2, 2, 2, 2, 3,
    Coordinates(0, 1, 1, 1),
    boxes = Moveables(listOf(
        Box(Coordinates(1, 1, 1, 1)),
        Box(Coordinates(0, 1, 1, 2)),
        Box(Coordinates(0,1,0,1)),
    ))
)

val level4 = Level(3, 3, 1, 4, 4,
    Coordinates(1, 1, 0, 2),
    boxes = Moveables(listOf(
        Box(Coordinates(1, 0, 0, 0)),
        Box(Coordinates(1, 1, 0, 1)),
        Box(Coordinates(2,2,0,2)),
        Box(Coordinates(1, 2, 0, 3)))
    )
)

val level5 = Level(3, 3, 3, 5, 5,
    Coordinates(0, 1, 0, 3),
    boxes = Moveables(listOf(
        Box(Coordinates(2, 1, 1, 0)),
        Box(Coordinates(2, 1, 2, 3)),
        Box(Coordinates(2,0,2,4)),
        Box(Coordinates(0, 1, 1, 4))
    ))
)

val level6 = Level(5, 5, 1, 3, 6, Coordinates(2, 0, 0, 2,), tesseracts = Moveables(listOf(
    Tesseract(0, 2, 0, 0..2)
)))

//endregion

var currentLevel = level1
fun getLevel() : Int {
    return if (currentLevel == level1) 1
    else if (currentLevel == level2) 2
    else if (currentLevel == level3) 2
    else if (currentLevel == level4) 4
    else if (currentLevel == level0) 0
    else 5
}

fun prepLevel(level: Level) {
    player = level.start
    if (level.boxes != null) {
        boxes = level.boxes
    }
    if (level.tesseracts != null) {
        tesseracts = level.tesseracts
    }
    currentLevel = level
}

var hasStarted1 = false
var hasStarted2 = false
var hasStarted3 = false
var hasStarted4 = false
var hasStarted5 = false

var intro1 = false
var intro2 = false
var intro3 = false
var intro4 = false
var intro5 = false
var intro6 = false
var intro7 = false

var flapExplianed = false

val flapExplination = "The opening is too small for you to squeeze through.\nIn fact, it's roughly the size and shape of a box... or a tesseract segment."

val mainGame = game {

    action("Go to checkpoint (.*)") { (code) ->
        var codeInt = code.toInt()
        player.w = codeInt % 10
        codeInt /= 10
        player.z = codeInt % 10
        codeInt /= 10
        player.y = codeInt % 10
        codeInt /= 10
        player.x = codeInt % 10
        codeInt /= 10
        currentLevel = when (codeInt) {
            0 -> level0
            1 -> level1
            2 -> level2
            3 -> level3
            4 -> level4
            else -> level5
        }
        go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
    }

    //region test rooms
    level0.rooms[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf("north", "east"),)
    level0.rooms[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("north", "west"),)
    level0.rooms[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf(), ladderDirection = "up")
    level0.rooms[1][1][0][0] = mazeRoom(number = "1.1.0", color = "red", doors = listOf("south",))

    level0.rooms[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf(),)
    level0.rooms[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", flaps=listOf("north",), doors = listOf(),)
    level0.rooms[1][0][1][0] = mazeRoom(number = "1.0.1", color = "red", doors = listOf(), ladderDirection = "down",)
    level0.rooms[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf(),)

    level0.rooms[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("east"), ladderDirection = "up")
    level0.rooms[0][1][0][1] = mazeRoom(
        number = "0.1.0",
        color = "blue",
        doors = listOf("north", "west"),

        )
    level0.rooms[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf(),)
    level0.rooms[1][1][0][1] = mazeRoom(
        number = "1.1.0",
        color = "blue",
        doors = listOf("south"),
    )

    level0.rooms[0][0][1][1] = mazeRoom(
        number = "0.0.1",
        color = "blue",
        doors = listOf("north", "east"),
        ladderDirection = "down",

    )
    level0.rooms[0][1][1][1] = mazeRoom(
        number = "0.1.1",
        color = "blue",
        doors = listOf("north", "west"),
    )
    level0.rooms[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf("south", "east"),)
    level0.rooms[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("south", "west"),)

    level0.rooms[0][0][0][2] = mazeRoom(number = "0.0.0", color = "green", doors = listOf("east"),)
    level0.rooms[0][1][0][2] = mazeRoom(
        number = "0.1.0",
        color = "green",
        doors = listOf("west"),
        )
    level0.rooms[1][0][0][2] = mazeRoom(
        number = "1.0.0",
        color = "green",
        doors = listOf(),
    )
    level0.rooms[1][1][0][2] = mazeRoom(number = "1.1.0", color = "green", doors = listOf(),)

    level0.rooms[0][0][1][2] = mazeRoom(
        number = "0.0.1",
        color = "green",
        doors = listOf("east"),
    )
    level0.rooms[0][1][1][2] = mazeRoom(
        number = "0.1.1",
        color = "green",
        doors = listOf("west", "north"),
    )
    level0.rooms[1][0][1][2] = mazeRoom(number = "1.0.1", color = "green", flaps = listOf("east"), doors = listOf(),)
    level0.rooms[1][1][1][2] = mazeRoom(number = "1.1.1", color = "green", flaps = listOf("west"), doors = listOf("south"),)

    //endregion

    //region level1 rooms
    level1.rooms[0][0][0][0] = mazeRoom(
            number = "0.0.0",
            color = "red",
            doors = listOf(),
            ladderDirection = "up",
            other = "You finished Level 1! Would you like to go on? If so, say, 'yes.'",
            isFinish = true
    ) {
        action("yes") {
            prepLevel(level2)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
    }
    level1.rooms[0][1][0][0] = mazeRoom(
            number = "0.1.0",
            color = "red",
            doors = listOf(),
            ladderDirection = "up",
    )

    level1.rooms[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("east"),)
    level1.rooms[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf("west"), ladderDirection = "down", )

    level1.rooms[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf(), ladderDirection = "up")
    level1.rooms[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf(),)

    level1.rooms[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("east"), ladderDirection = "down")
    level1.rooms[0][1][1][1] = mazeRoom(number = "0.1.1", color = "blue", doors = listOf("west"),)
    //endregion

    //region level2 rooms
    level2.rooms[0][0][0][0] = mazeRoom(
            number = "0.0.0",
            color = "red",
            doors = listOf("east", ),
    )
    level2.rooms[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("west",),)
    level2.rooms[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf("east",),)
    level2.rooms[1][1][0][0] = mazeRoom(number = "1.1.0", color = "red", doors = listOf("west"),)

    level2.rooms[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("east"),)
    level2.rooms[0][1][1][0] = mazeRoom(number= "0.1.1", color = "red", doors = listOf("west"),)
    level2.rooms[1][0][1][0] = mazeRoom(number = "1.0.1", color = "red", doors = listOf("east"),)
    level2.rooms[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf("west"),)

    level2.rooms[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("north", "east"),)
    level2.rooms[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf("west", ),)
    level2.rooms[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south",),)
    level2.rooms[1][1][0][1] = mazeRoom(number = "1.1.0", color = "blue", doors = listOf(), ladderDirection = "up")

    level2.rooms[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("west"), ladderDirection = "down",)
    level2.rooms[0][1][1][1] = mazeRoom(
            number = "0.1.1",
            color = "blue",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 2! To go on to Level 3 say 'yes.'"
    ) {
        action("yes") {
            prepLevel(level3)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
    }
    level2.rooms[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("north",),)
    level2.rooms[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf("south", "east"),)
    //endregion

    //region level3 rooms
    //region level3 red
    level3.rooms[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf(),)
    level3.rooms[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("north"),)
    level3.rooms[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf(), ladderDirection = "up")
    level3.rooms[1][1][0][0] = mazeRoom(
            number = "1.1.0",
            color = "red",
            doors = listOf("south",),
    )


    level3.rooms[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf(),)
    level3.rooms[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf(),)
    level3.rooms[1][0][1][0] = mazeRoom(
            number = "1.0.1",
            color = "red",
            doors = listOf(),
            ladderDirection = "down",
            lock = "down",
            link = Coordinates(0,0, 1, 2),
            lockLetter = "C"
    )
    level3.rooms[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf(),)
    //endregion

    //region level3 blue
    level3.rooms[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("east"), ladderDirection = "up")
    level3.rooms[0][1][0][1] = mazeRoom(
            number = "0.1.0",
            color = "blue",
            doors = listOf("north", "west"),

    )
    level3.rooms[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf(),)
    level3.rooms[1][1][0][1] = mazeRoom(
            number = "1.1.0",
            color = "blue",
            doors = listOf("south"),
            hasPlate = true,
            plateLetter = "blue B",
    )

    level3.rooms[0][0][1][1] = mazeRoom(
            number = "0.0.1",
            color = "blue",
            doors = listOf("north", "east"),
            ladderDirection = "down",
            lock = "north",
            link = Coordinates(1, 1,0,1),
            lockLetter = "B"

    )
    level3.rooms[0][1][1][1] = mazeRoom(
            number = "0.1.1",
            color = "blue",
            doors = listOf("north", "west"),
            hasPlate = true,
            plateLetter = "blue and green A",
            lock = "west",
            link = Coordinates(x = 0, y = 1, z = 1, w = 1),
            lockLetter = "A"
    )
    level3.rooms[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf("south"),)
    level3.rooms[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("south"),)
    //endregion

    //region level3 green
    level3.rooms[0][0][0][2] = mazeRoom(number = "0.0.0", color = "green", doors = listOf("east"),)
    level3.rooms[0][1][0][2] = mazeRoom(
            number = "0.1.0",
            color = "green",
            doors = listOf("west"),

    )
    level3.rooms[1][0][0][2] = mazeRoom(
            number = "1.0.0",
            color = "green",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 3! Would you like to go on to Level 4? If so, say, 'yes.'"
    ) {
        action("yes") {
            prepLevel(level4)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
    }
    level3.rooms[1][1][0][2] = mazeRoom(number = "1.1.0", color = "green", doors = listOf(),)

    level3.rooms[0][0][1][2] = mazeRoom(
            number = "0.0.1",
            color = "green",
            doors = listOf("east"),
            hasPlate = true,
            plateLetter = "red C",
            lock = "east",
            link = Coordinates(0,1,1,1),
            lockLetter = "A"
    )
    level3.rooms[0][1][1][2] = mazeRoom(
            number = "0.1.1",
            color = "green",
            doors = listOf("west", "north"),
            lock = "west",
            link = Coordinates(0, 1, 1, 1),
            lockLetter = "A"
    )
    level3.rooms[1][0][1][2] = mazeRoom(number = "1.0.1", color = "green", doors = listOf(),)
    level3.rooms[1][1][1][2] = mazeRoom(number = "1.1.1", color = "green", doors = listOf("south"),)
    //endregion
    //endregion

    //region level4 rooms
    //region level4 red
    level4.rooms[0][0][0][0] = mazeRoom(
            number = "0.0",
            color = "red",
            doors = listOf("east"),
            lock = "east",
            link = Coordinates(0, 1, 0, 3),
            lockLetter = "D"
    )
    level4.rooms[0][1][0][0] = mazeRoom(
            number = "0.1",
            color = "red",
            doors = listOf("north", "east", "west"),
            lock = "west",
            link = Coordinates(0, 1, 0, 3),
            lockLetter = "D"
    )
    level4.rooms[0][2][0][0] = mazeRoom(number = "0.2", color = "red", doors = listOf("north", "west"))

    level4.rooms[1][0][0][0] = mazeRoom(
            number = "1.0",
            color = "red",
            doors = listOf("east"),
            lock = "east",
            link = Coordinates(0, 1, 0, 2),
            lockLetter = "B"
    )
    level4.rooms[1][1][0][0] = mazeRoom(
            number = "1.1",
            color = "red",
            doors = listOf("north", "south", "east", "west"),
            lock = "west",
            link = Coordinates(0, 1, 0, 2),
            hasPlate = true,
            plateLetter = "yellow A",
            lockLetter = "B"
    )
    level4.rooms[1][2][0][0] = mazeRoom(number = "1.2", color = "red", doors = listOf("north", "south", "west"))

    level4.rooms[2][0][0][0] = mazeRoom(number = "2.0", color = "red", doors = listOf())
    level4.rooms[2][1][0][0] = mazeRoom(number = "2.1", color = "red", doors = listOf("south", "east"))
    level4.rooms[2][2][0][0] = mazeRoom(number = "2.2", color = "red", doors = listOf("south", "west"))
    //endregion

    //region level4 blue
    level4.rooms[0][0][0][1] = mazeRoom(
            number = "0.0",
            color = "blue",
            doors = listOf("north"),
            lock = "north",
            link = Coordinates(0, 1, 0, 2),
            lockLetter = "B"
    )
    level4.rooms[0][1][0][1] = mazeRoom(number = "0.1", color = "blue", doors = listOf("north"))
    level4.rooms[0][2][0][1] = mazeRoom(
            number = "0.2",
            color = "blue",
            doors = listOf("north"),
            hasPlate = true,
            plateLetter = "yellow C",
            lock = "north",
            lockLetter = "F",
            link = Coordinates(2,2,0,2)
    )

    level4.rooms[1][0][0][1] = mazeRoom(
            number = "1.0",
            color = "blue",
            doors = listOf("south"),
            lock = "north",
            link = Coordinates(0, 1, 0, 2),
            lockLetter = "B"
    )
    level4.rooms[1][1][0][1] = mazeRoom(number = "1.1", color = "blue", doors = listOf("north", "south", "east"))
    level4.rooms[1][2][0][1] = mazeRoom(
            number = "1.2",
            color = "blue",
            doors = listOf("north", "south", "west"),
            lock = "south",
            lockLetter = "F",
            link = Coordinates(2,2,0,2)
    )

    level4.rooms[2][0][0][1] = mazeRoom(number = "2.0", color = "blue", doors = listOf())
    level4.rooms[2][1][0][1] = mazeRoom(
            number = "2.1",
            color = "blue",
            doors = listOf("south", "east"),
            hasPlate = true,
            plateLetter = "yellow E",
    )
    level4.rooms[2][2][0][1] = mazeRoom(number = "2.2", color = "blue", doors = listOf("south", "west"))
    //endregion

    //region level4 green
    level4.rooms[0][0][0][2] = mazeRoom(number = "0.0", color = "green", doors = listOf())
    level4.rooms[0][1][0][2] = mazeRoom(
            number = "0.1",
            color = "green",
            doors = listOf("north", "east"),
            hasPlate = true,
            plateLetter = "blue B"
    )
    level4.rooms[0][2][0][2] = mazeRoom(number = "0.2", color = "green", doors = listOf("north", "west"))

    level4.rooms[1][0][0][2] = mazeRoom(number = "1.0", color = "green", doors = listOf())
    level4.rooms[1][1][0][2] = mazeRoom(number = "1.1", color = "green", doors = listOf("north", "south", "east"))
    level4.rooms[1][2][0][2] = mazeRoom(number = "1.2", color = "green", doors = listOf("north", "south", "west"))

    level4.rooms[2][0][0][2] = mazeRoom(
            number = "2.0",
            color = "green",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 4! If you'd like to go on to Level 5, say, 'yes.'") {
        action("yes", "yes.", "Yes", "Yes.") {
            prepLevel(level5)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
    }
    level4.rooms[2][1][0][2] = mazeRoom(number = "2.1", color = "green", doors = listOf("south", "east"))
    level4.rooms[2][2][0][2] = mazeRoom(
            number = "2.2",
            color = "green",
            doors = listOf("south", "west"),
            hasPlate = true,
            plateLetter = "blue F"
    )
    //endregion

    //region level4 yellow
    level4.rooms[0][0][0][3] = mazeRoom(number = "0.0", color = "yellow", doors = listOf())
    level4.rooms[0][1][0][3] = mazeRoom(
            number = "0.1",
            color = "yellow",
            doors = listOf("north", "east"),
            hasPlate = true,
            plateLetter = "red D",
            lock = "east",
            link = Coordinates(0, 2, 0, 1),
            lockLetter = "C",

    )
    level4.rooms[0][2][0][3] = mazeRoom(
            number = "0.2",
            color = "yellow",
            doors = listOf("north", "west"),
            lock = "west",
            link = Coordinates(0,2,0,1),
            lockLetter = "C"
    )

    level4.rooms[1][0][0][3] = mazeRoom(
            number = "1.0",
            color = "yellow",
            doors = listOf("north"),
            lock = "north",
            link = Coordinates(1, 1, 0, 0),
            lockLetter = "A"
    )
    level4.rooms[1][1][0][3] = mazeRoom(
            number = "1.1",
            color = "yellow",
            doors = listOf("north", "south",),

    )
    level4.rooms[1][2][0][3] = mazeRoom(
            number = "1.2",
            color = "yellow",
            doors = listOf("north", "south",),
            lock = "south",
            link = Coordinates(2, 1, 0, 1),
            lockLetter = "E"
    )

    level4.rooms[2][0][0][3] = mazeRoom(
            number = "2.0",
            color = "yellow",
            doors = listOf("south"),
            lock = "south",
            link = Coordinates(1, 1, 0, 0),
            lockLetter = "A"
    )
    level4.rooms[2][1][0][3] = mazeRoom(number = "2.1", color = "yellow", doors = listOf("south",))
    level4.rooms[2][2][0][3] = mazeRoom(number = "2.2", color = "yellow", doors = listOf("south",))
    //endregion
    //endregion

    //region level5 rooms
    //region level5 red
    //region level5 red 1st
    level5.rooms[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf("east"), ladderDirection = "up")
    level5.rooms[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("west", "east"))
    level5.rooms[0][2][0][0] = mazeRoom(number = "0.2.0", color = "red", doors = listOf("west"))
    //endregion

    //region level5 red 2nd
    level5.rooms[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf(), ladderDirection = "up down")
    level5.rooms[0][2][1][0] = mazeRoom(
            number = "0.2.1",
            color = "red",
            doors = listOf("north"),
            hasPlate = true,
            plateLetter = "green A"
    )
    level5.rooms[1][2][1][0] = mazeRoom(number = "1.2.1", color = "red", doors = listOf("south", "north"),)

    level5.rooms[2][0][1][0] = mazeRoom(
            number = "2.0.1",
            color = "red",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 5! Congratulations! Would you like to go on to level six? If so, say 'yes'."
    ) {
        action("yes") {
            prepLevel(level6)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
    }
    level5.rooms[2][1][1][0] = mazeRoom(
            number = "2.1.1",
            color = "red",
            doors = listOf("east"),
            lock = "east",
            link = Coordinates(2, 0, 1, 4),
            lockLetter = "B"
    )
    level5.rooms[2][2][1][0] = mazeRoom(
            number = "2.2.1",
            color = "red",
            doors = listOf("west", "south"),
    )
    //endregion

    //region level5 red 3rd
    level5.rooms[0][0][2][0] = mazeRoom(number = "0.0.2", color = "red", doors = listOf("north"), ladderDirection = "down")

    level5.rooms[1][0][2][0] = mazeRoom(
            number = "1.0.2",
            color = "red",
            doors = listOf("north", "south", "east"),
            lock = "north",
            link = Coordinates(0, 0, 2, 4),
            lockLetter = "C"
    )
    level5.rooms[1][1][2][0] = mazeRoom(number = "1.1.2", color = "red", doors = listOf("north", "west"))

    level5.rooms[2][0][2][0] = mazeRoom(number = "2.0.2", color = "red", doors = listOf("south", "east"))
    level5.rooms[2][1][2][0] = mazeRoom(
            number = "2.1.2",
            color = "red",
            doors = listOf("south", "west"),
            lock = "west",
            lockLetter = "C",
            link = Coordinates(0, 0, 2, 4),
    )
    //endregion
    //endregion

    //region level5 blue
    //region level5 blue 1st
    level5.rooms[0][2][0][1] = mazeRoom(number = "0.2.0", color = "blue", doors = listOf("north"),)

    level5.rooms[1][2][0][1] = mazeRoom(number = "1.2.0", color = "blue", doors = listOf("south"),)

    level5.rooms[2][0][0][1] = mazeRoom(number = "2.0.0", color = "blue", doors = listOf("east"),)
    level5.rooms[2][1][0][1] = mazeRoom(number = "2.1.0", color = "blue", doors = listOf("west"),)
    //endregion

    //region level5 blue 2nd
    level5.rooms[0][0][1][1] = mazeRoom(
            number = "0.0.1",
            color = "blue",
            doors = listOf(),
            ladderDirection = "up",
            other = "You've reached the second CHECKPOINT! The next checkpoint is marked 0.2.2 in orange."
    )
    level5.rooms[0][1][1][1] = mazeRoom(number = "0.1.1", color = "blue", doors = listOf("north", "east"),)
    level5.rooms[0][2][1][1] = mazeRoom(number = "0.2.1", color = "blue", doors = listOf("west"),)

    level5.rooms[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf(),)
    level5.rooms[1][1][1][1] = mazeRoom(
            number = ".1",
            color = "blue",
            doors = listOf("north", "south"),
            lock = "north",
            link = Coordinates(2, 0, 1, 4),
            lockLetter = "B"
    )

    level5.rooms[2][0][1][1] = mazeRoom(number = "2.0.1", color = "blue", doors = listOf(), ladderDirection = "up")
    level5.rooms[2][1][1][1] = mazeRoom(number = "2.1.1", color = "blue", doors = listOf("south"),)
    //endregion

    //region level5 blue 3rd
    level5.rooms[0][0][2][1] = mazeRoom(number = "0.0.2", color = "blue", doors = listOf("north", "east"), ladderDirection = "down")
    level5.rooms[0][1][2][1] = mazeRoom(number = "0.1.2", color = "blue", doors = listOf("north", "east", "west"),)
    level5.rooms[0][2][2][1] = mazeRoom(number = "0.2.2", color = "blue", doors = listOf("west"),)

    level5.rooms[1][0][2][1] = mazeRoom(number = "1.0.2", color = "blue", doors = listOf("south", "east"),)
    level5.rooms[1][1][2][1] = mazeRoom(number = "1.1.2", color = "blue", doors = listOf("north", "south", "west"),)
    level5.rooms[1][2][2][1] = mazeRoom(number = "1.2.2", color = "blue", doors = listOf("north"),)

    level5.rooms[2][0][2][1] = mazeRoom(number = "2.0.2", color = "blue", doors = listOf(), ladderDirection = "down")
    level5.rooms[2][1][2][1] = mazeRoom(number = "2.1.2", color = "blue", doors = listOf("south"),)
    level5.rooms[2][2][2][1] = mazeRoom(number = "2.2.2", color = "blue", doors = listOf("south"),)
    //endregion
    //endregion

    //region level5 green
    //region level5 green 1st
    level5.rooms[1][0][0][2] = mazeRoom(number = "1.0.0", color = "green", doors = listOf("north"),)
    level5.rooms[1][2][0][2] = mazeRoom(number = "1.2.0", color = "green", doors = listOf(),)

    level5.rooms[2][0][0][2] = mazeRoom(number = "2.0.0", color = "green", doors = listOf("south"),)
    level5.rooms[2][1][0][2] = mazeRoom(number = "2.1.0", color = "green", doors = listOf(),)
    //endregion

    //region level5 green 2nd
    level5.rooms[0][0][1][2] = mazeRoom(
            number = "0.0.1",
            color = "green",
            doors = listOf("east"),
            lock = "east",
            lockLetter = "A",
            link = Coordinates(0, 2, 1, 0)
    )
    level5.rooms[0][1][1][2] = mazeRoom(
            number = "0.1.1",
            color = "green",
            doors = listOf("west", "north"),
            lock = "west",
            lockLetter = "A",
            link = Coordinates(0,2,1,0)
    )

    level5.rooms[1][1][1][2] = mazeRoom(
            number = "1.1.1",
            color = "green",
            doors = listOf("north", "south"),
            lock = "north",
            lockLetter = "B",
            link = Coordinates(2,0,1,4),
    )

    level5.rooms[2][1][1][2] = mazeRoom(number = "2.1.1", color = "green", doors = listOf("south"),)
    //endregion

    //region level5 green 3rd
    level5.rooms[0][0][2][2] = mazeRoom(number = "0.0.2", color = "green", doors = listOf("north", "east"),)
    level5.rooms[0][1][2][2] = mazeRoom(number = "0.1.2", color = "green", doors = listOf("north", "west"),)

    level5.rooms[1][0][2][2] = mazeRoom(number = "1.0.2", color = "green", doors = listOf("south", "east"),)
    level5.rooms[1][1][2][2] = mazeRoom(
            number = "1.1.2",
            color = "green",
            doors = listOf("south", "east", "west"),
            lock = "east",
            lockLetter = "C",
            link = Coordinates(0,0, 2, 4)
    )
    level5.rooms[1][2][2][2] = mazeRoom(
            number = "1.2.2",
            color = "green",
            doors = listOf("west"),
            lock = "west",
            lockLetter = "C",
            link = Coordinates(0,0,2,4)
    )

    level5.rooms[2][2][2][2] = mazeRoom(number = "2.2.2", color = "green", doors = listOf(),)
    //endregion
    //endregion

    //region level5 yellow
    //region level5 yellow 1st
    level5.rooms[0][0][0][3] = mazeRoom(number = "0.0.0", color = "yellow", doors = listOf("east", "north"),)
    level5.rooms[0][1][0][3] = mazeRoom(number = "0.1.0", color = "yellow", doors = listOf("west"),)
    level5.rooms[0][2][0][3] = mazeRoom(number = "0.2.0", color = "yellow", doors = listOf("north"),)

    level5.rooms[1][0][0][3] = mazeRoom(number = "1.0.0", color = "yellow", doors = listOf("south"),)
    level5.rooms[1][2][0][3] = mazeRoom(number = "1.2.0", color = "yellow", doors = listOf("south"),)

    level5.rooms[2][1][0][3] = mazeRoom(number = "2.1.0", color = "yellow", doors = listOf(),)
    level5.rooms[2][2][0][3] = mazeRoom(
            number = "2.2.0",
            color = "yellow",
            doors = listOf(),
            ladderDirection = "up",
            other = "You've reached the first CHECKPOINT! Your next CHECKPOINT is the room marked 0.0.1 in blue."
    )
    //endregion

    //region level5 yellow 2nd

    level5.rooms[1][1][1][3] = mazeRoom(
            number = "1.1.1",
            color = "yellow",
            doors = listOf("north", "east"),
            lock = "north",
            lockLetter = "B",
            link = Coordinates(2, 0, 1, 4)
    )
    level5.rooms[1][2][1][3] = mazeRoom(number = "1.2.1", color = "yellow", doors = listOf("west", "north"),)

    level5.rooms[2][1][1][3] = mazeRoom(number = "2.1.1", color = "yellow", doors = listOf("south"),)
    level5.rooms[2][2][1][3] = mazeRoom(number = "2.2.1", color = "yellow", doors = listOf("south"), ladderDirection = "down")
    //endregion

    //region level5 yellow 3rd

    level5.rooms[1][2][2][3] = mazeRoom(number = "1.2.2", color = "yellow", doors = listOf(),)

    level5.rooms[2][1][2][3] = mazeRoom(number = "2.1.2", color = "yellow", doors = listOf("east"),)
    level5.rooms[2][2][2][3] = mazeRoom(
            number = "2.2.2",
            color = "yellow",
            doors = listOf("west"),
            hasPlate = true,
            plateLetter = "orange D"
    )
    //endregion
    //endregion

    //region level5 orange
    //region level5 orange 1st
    level5.rooms[0][2][0][4] = mazeRoom(number = "0.2.0", color = "orange", doors = listOf(), ladderDirection = "up")

    level5.rooms[2][0][0][4] = mazeRoom(number = "2.0.0", color = "orange", doors = listOf("east"),)
    level5.rooms[2][1][0][4] = mazeRoom(number = "2.1.0", color = "orange", doors = listOf("east", "west"),)
    level5.rooms[2][2][0][4] = mazeRoom(number = "2.2.0", color = "orange", doors = listOf("west"),)
    //endregion

    //region level5 orange 2nd
    level5.rooms[0][0][1][4] = mazeRoom(number = "0.0.1", color = "orange", doors = listOf("north", "east"),)
    level5.rooms[0][1][1][4] = mazeRoom(number = "0.1.1", color = "orange", doors = listOf("west"),)
    level5.rooms[0][2][1][4] = mazeRoom(number = "0.2.1", color = "orange", doors = listOf(), ladderDirection = "up down")

    level5.rooms[1][0][1][4] = mazeRoom(number = "1.0.1", color = "orange", doors = listOf("north", "south"),)

    level5.rooms[2][0][1][4] = mazeRoom(
            number = "2.0.1",
            color = "orange",
            doors = listOf("south", "east"),
            hasPlate = true,
            plateLetter = "B"
    )
    level5.rooms[2][1][1][4] = mazeRoom(number = "2.1.1", color = "orange", doors = listOf("west"),)
    //endregion

    //region level5 orange 3rd
    level5.rooms[0][0][2][4] = mazeRoom(
            number = "0.0.2",
            color = "orange",
            doors = listOf("east"),
            lock = "east",
            lockLetter = "D",
            link = Coordinates(2,2,2,3),
            hasPlate = true,
            plateLetter = "green C"
    )
    level5.rooms[0][1][2][4] = mazeRoom(
            number = "0.1.2",
            color = "orange",
            doors = listOf("north", "east", "west"),
            lock = "west",
            lockLetter = "D",
            link = Coordinates(2,2,2,3)
    )
    level5.rooms[0][2][2][4] = mazeRoom(
            number = "0.2.2",
            color = "orange",
            doors = listOf("west"),
            ladderDirection = "down",
            other = "You've reached the third CHECKPOINT! The FINAL room in Level 5 is marked 0.2.1 in red."
    )

    level5.rooms[1][0][2][4] = mazeRoom(number = "1.0.2", color = "orange", doors = listOf("east", "north"),)
    level5.rooms[1][1][2][4] = mazeRoom(number = "1.1.2", color = "orange", doors = listOf("east", "west", "south"),)
    level5.rooms[1][2][2][4] = mazeRoom(number = "1.2.2", color = "orange", doors = listOf("west"),)

    level5.rooms[2][0][2][4] = mazeRoom(number = "2.0.2", color = "orange", doors = listOf("south"),)
    //endregion
    //endregion

    //endregion

    //region tutorial
    val tutorial = room() {
        onEnter { if (!hasStarted1) {
            say("Welcome to Shift! Please read these instructions carefully!")
            say("In this game you will explore a series of four-dimensional mazes.")
            say("PRESS THE ENTER KEY NOW TO CONTINUE")
            hasStarted1 = true
        }
        }
        action("") {
            if(!intro1) {
                say("The room you're currently in will always be shown by the most recent box, " +
                        "which will look something like this:")
                say("""
                    ┌─────┐
                    │     │
                    └─────┘
                    """.trimIndent())
                say("PRESS THE ENTER KEY NOW TO CONTINUE")
                intro1 = true
            }
            else if (!intro2) {
                say("Doors are represented by this symbol: ║ for east or west.")
                say("And by this symbol: ═══ for north and south.")
                say("Ladders, both up and down, are represented by this: #")
                say("Read the text that appears below the current room to know whether a ladder goes up or down, " +
                        "and any other info about the room.")
                say("PRESS THE ENTER KEY NOW TO CONTINUE")
                intro2 = true
            }
            else if (!intro3) {
                say("To navigate these mazes, type 'go north', 'go south', 'go east', or 'go west' and hit enter to go through doors.")
                say("type, 'go up', or 'go down' and hit enter to climb ladders up or down.")
                say("Remember when you're looking at the screen, north is toward the top of the screen, " +
                        "south is toward the bottom, east is right, and west is left.")
                say("PRESS THE ENTER KEY NOW TO CONTINUE")
                intro3 = true
            }
            else if (!intro4) {
                say("If you ever get confused about directions, you can say, 'show compass,' and this will appear on your screen:")
                say("""
                       ▲
                       N
                   ◄W    E►
                       S
                       ▼
                       
                    """.trimIndent())
                say("If you get stumped, you can say 'show map' to get a link to the map of your current level.")
                intro4 = true
            }
            else if (!intro5) {
                say("In addition to moving in the three dimensions we're familiar with via ladders and doors, " +
                        "you can also shift (meaning move) ana or kata, which are directions in the FOURTH DIMENSION.")
                say("This is a special power you can do ANYWHERE, ANYTIME to move from one Hyperplane to another.")
                say("Remember, ana and kata are directions along a FOURTH axis, so you when you shift (meaning move) ana or shift kata, " +
                        "you won't go up, down, north, south, east or west. You'll go ana, or kata.")
                say("They're two entirely different spacial directions.")
                say("Once you begin playing, you'll see how this works.")
                say("PRESS ENTER NOW TO CONTINUE")
                intro5 = true
            }
            else if (!intro6) {
                say("To shift ana or kata, just type these two words: 'shift ana' or 'shift kata' and press ENTER.")
                say("That DOES NOT mean pressing the SHIFT key on your computer.")
                say("Just type in the word 'shift', all lower case, followed by the word 'ana' or 'kata' and then press ENTER.")
                say("If you get stuck, try re-reading these instructions.")
                say("TO BEGIN LEVEL 1, PRESS THE ENTER KEY NOW")
                intro6 = true
            }
            else if (!intro7) {
                say("WELCOME TO LEVEL 1!")
                say("HINT: You're looking for the room with 0.0.0 marked in red.")
                intro7 = true
                go(level1.rooms[player.x][player.y][player.z][player.w]!!.room)
            }
        }
        action ("go to test") {
            player.x = 0
            player.y = 1
            player.z = 1
            player.w = 1
            currentLevel = level0
            tesseracts = Moveables(
                listOf(
                    Tesseract(1, 1, 0, 0..2),
                    Tesseract(0, 0, 1, 1..2)
                )
            )
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
        action("go to level two"){
            prepLevel(level2)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
        action("go to level three") {
            prepLevel(level3)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
        action ("go to level four") {
            prepLevel(level4)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
        action ("go to level five") {
            prepLevel(level5)
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
        action ("go to level six") {
            prepLevel(level6)
            say("Welcome to Level 6! Your goal is to get to Blue 4.0.0")
            say("This level has no map, but you're free to draw your own as you go.")
            say("Level 6 also introduces Tesseracts. Tesseracts work just like boxes, except they extend across multiple 4D hyperplanes.")
            say("If you move a Tesseract in one Hyperplane, it moves in each other Hyperplane that it covers.")
            say("If 4D \"slice\" of a Tesseract can't move in a certain direction, then the Tesseract can't move, so you'll have to ")
            say("flip back and fourth across Hyperplanes to find where to move it.")
            go(currentLevel.rooms[player.x][player.y][player.z][player.w]!!.room)
        }
    }

    // level 6 red

    level6.rooms[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors=listOf("east"))
    level6.rooms[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf("north"))
    level6.rooms[2][0][0][0] = mazeRoom(number = "2.0.0", color = "red", doors = listOf("south"))
    level6.rooms[3][0][0][0] = mazeRoom(number = "3.0.0", color = "red", doors = listOf())

    level6.rooms[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("west"))
    level6.rooms[3][1][0][0] = mazeRoom(number = "3.1.0", color = "red", doors = listOf())
    level6.rooms[4][1][0][0] = mazeRoom(number = "4.1.0", color = "red", doors = listOf())

    level6.rooms[0][2][0][0] = mazeRoom(number = "0.2.0", color = "red", doors = listOf("east"))
    level6.rooms[2][2][0][0] = mazeRoom(number = "2.2.0", color = "red", flaps = listOf("north"), doors = listOf("east"))
    level6.rooms[3][2][0][0] = mazeRoom(number = "3.2.0", color = "red", flaps = listOf("south"), doors = listOf("east"))
    level6.rooms[4][2][0][0] = mazeRoom(number = "4.2.0", color = "red", doors = listOf("east"))

    level6.rooms[0][3][0][0] = mazeRoom(number = "0.3.0", color = "red", doors = listOf("north", "east", "west"))
    level6.rooms[1][3][0][0] = mazeRoom(number = "1.3.0", color = "red", doors = listOf("north", "south", "east"))
    level6.rooms[2][3][0][0] = mazeRoom(number = "2.3.0", color = "red", doors = listOf("south", "east", "west"))
    level6.rooms[3][3][0][0] = mazeRoom(number = "3.3.0", color = "red", flaps = listOf("east"), doors = listOf("west"))
    level6.rooms[4][3][0][0] = mazeRoom(number = "4.3.0", color = "red", doors = listOf("west"))

    level6.rooms[0][4][0][0] = mazeRoom(number = "0.4.0", color = "red", doors = listOf("north", "west"))
    level6.rooms[1][4][0][0] = mazeRoom(number = "1.4.0", color = "red", doors = listOf("north", "south", "west"))
    level6.rooms[2][4][0][0] = mazeRoom(number = "2.4.0", color = "red", flaps = listOf("north"), doors = listOf("south", "west",))
    level6.rooms[3][4][0][0] = mazeRoom(number = "3.4.0", color = "red", flaps = listOf("west", "south"), doors = listOf())
    level6.rooms[4][4][0][0] = mazeRoom(number = "4.4.0", color = "red", doors = listOf())

    // level 6 blue

    level6.rooms[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("north"))
    level6.rooms[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south"))
    level6.rooms[2][0][0][1] = mazeRoom(number = "2.0.0", color = "blue", doors = listOf())
    level6.rooms[4][0][0][1] = mazeRoom(
        number = "4.0.0",
        color = "blue",
        doors = listOf(),
        isFinish = true,
        other = "You beat the game! Nice work!"
    )

    level6.rooms[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf())
    level6.rooms[1][1][0][1] = mazeRoom(number = "1.1.0", color = "blue", doors = listOf())

    level6.rooms[0][2][0][1] = mazeRoom(number = "0.2.0", color = "blue", doors = listOf("north", "east"))
    level6.rooms[1][2][0][1] = mazeRoom(number = "1.2.0", color = "blue", doors = listOf("north", "south"))
    level6.rooms[2][2][0][1] = mazeRoom(
        number = "2.2.0",
        color = "blue",
        flaps = listOf("north",),
        doors = listOf("south", "east"),
        hasPlate = true,
        plateLetter = "green A",

    )
    level6.rooms[3][2][0][1] = mazeRoom(number = "3.2.0", color = "blue", flaps = listOf("south", "east",), doors = listOf())
    level6.rooms[4][2][0][1] = mazeRoom(number = "4.2.0", color = "blue", doors = listOf())

    level6.rooms[0][3][0][1] = mazeRoom(number = "0.3.0", color = "blue", doors = listOf("north", "east", "west"))
    level6.rooms[1][3][0][1] = mazeRoom(number = "1.3.0", color = "blue", doors = listOf("north", "south", "east"))
    level6.rooms[2][3][0][1] = mazeRoom(number = "2.3.0", color = "blue", doors = listOf("north", "south"))
    level6.rooms[3][3][0][1] = mazeRoom(number = "3.3.0", color = "blue", flaps = listOf("west", "east"), doors = listOf("north", "south", ))
    level6.rooms[4][3][0][1] = mazeRoom(number = "4.3.0", color = "blue", doors = listOf("south"))

    level6.rooms[0][4][0][1] = mazeRoom(number = "0.4.0", color = "blue", doors = listOf("north", "west"))
    level6.rooms[1][4][0][1] = mazeRoom(number = "1.4.0", color = "blue", flaps = listOf("north",), doors = listOf("south", "west"))
    level6.rooms[2][4][0][1] = mazeRoom(number = "2.4.0", color = "blue", flaps = listOf("south"), doors = listOf("north",))
    level6.rooms[3][4][0][1] = mazeRoom(number = "3.4.0", color = "blue", doors = listOf("north", "west", "south"))
    level6.rooms[4][4][0][1] = mazeRoom(number = "4.4.0", color = "blue", doors = listOf("south", "west", "east"))

    // level 6 green

    level6.rooms[0][0][0][2] = mazeRoom(number = "0.0.0", color = "green", doors=listOf("north"))
    level6.rooms[1][0][0][2] = mazeRoom(number = "1.0.0", color = "green", doors = listOf("south"))
    level6.rooms[2][0][0][2] = mazeRoom(number = "2.0.0", color = "green", doors = listOf())
    level6.rooms[4][0][0][2] = mazeRoom(number = "4.0.0", color = "green", doors = listOf("east"))

    level6.rooms[0][1][0][2] = mazeRoom(number = "0.1.0", color = "green", doors = listOf("north", "east"))
    level6.rooms[1][1][0][2] = mazeRoom(number = "1.1.0", color = "green", doors = listOf("south"))
    level6.rooms[2][1][0][2] = mazeRoom(number = "2.1.0", color = "green", doors = listOf())
    level6.rooms[4][1][0][2] = mazeRoom(
        number = "4.1.0",
        color = "green",
        doors = listOf("west", "east"),
        lock = "west",
        link = Coordinates(2, 2, 0, 1),
        lockLetter = "A",
    )

    level6.rooms[0][2][0][2] = mazeRoom(number = "0.2.0", color = "green", doors = listOf("north", "east", "west"))
    level6.rooms[1][2][0][2] = mazeRoom(number = "1.2.0", color = "green", doors = listOf("south"))
    level6.rooms[2][2][0][2] = mazeRoom(number = "2.2.0", color = "green", doors = listOf("north", "east"))
    level6.rooms[3][2][0][2] = mazeRoom(number = "3.2.0", color = "green", flaps = listOf("east"), doors = listOf("south",))
    level6.rooms[4][2][0][2] = mazeRoom(number = "4.2.0", color = "green", doors = listOf("west"))

    level6.rooms[0][3][0][2] = mazeRoom(number = "0.3.0", color = "green", doors = listOf("east","west"))
    level6.rooms[1][3][0][2] = mazeRoom(number = "1.3.0", color = "green", doors = listOf("north", "east"))
    level6.rooms[2][3][0][2] = mazeRoom(number = "2.3.0", color = "green", doors = listOf("south", "east", "west"))
    level6.rooms[3][3][0][2] = mazeRoom(number = "3.3.0", color = "green", flaps = listOf("west"), doors = listOf("east",))

    level6.rooms[0][4][0][2] = mazeRoom(number = "0.4.0", color = "green", doors = listOf("west", "north"))
    level6.rooms[1][4][0][2] = mazeRoom(number = "1.4.0", color = "green", flaps = listOf("north",), doors = listOf("south", "west"))
    level6.rooms[2][4][0][2] = mazeRoom(number = "2.4.0", color = "green", flaps = listOf("south",), doors = listOf("north","west"))
    level6.rooms[3][4][0][2] = mazeRoom(number = "3.4.0", color = "green", doors = listOf("south", "west"))


    //endregion
    initialRoom = tutorial
}


fun main() {
    window.onload = { runGame(mainGame) }
}
