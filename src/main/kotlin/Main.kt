import dev.mythos.dsl.Room
import dev.mythos.dsl.game
import dev.mythos.dsl.room
import dev.mythos.game.runGame

data class Coordinates(
        var x: Int?,
        var y: Int?,
        var z: Int?,
        var w: Int?,
)

var player = Coordinates (x = 0, y = 1, z = 0, w = 0)

var boxes = listOf<Coordinates>(
)

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

val level3 =
        Array(2) {
            Array(2) {
                Array(2) {
                    Array<Room?>(3) {
                        null
                    }
                }
            }
        }

var currentLevel = level1

var hasStarted1 = false
var hasStarted2 = false
var hasStarted3 = false

var intro1 = false
var intro2 = false
var intro3 = false
var intro4 = false
var intro5 = false
var intro6 = false
var intro7 = false


val main = game {

    //region intro text

    //endregion

    //region level1 rooms
    level1[0][0][0][0] = mazeRoom(
            number = "0.0.0",
            color = "red",
            doors = listOf("east"),
            ladderDirection = "up",
            other = "You finished Level 1! Would you like to go on? If so, say, 'yes.'",
            isFinish = true
    ) {
        action("yes") {
            currentLevel = level2
            go(level2[0][0][0][0]!!)
        }
    }
    level1[0][1][0][0] = mazeRoom(
            number = "0.1.0",
            color = "red",
            doors = listOf(),
            ladderDirection = "up",
    )

    level1[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("east"),)
    level1[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf("west"), ladderDirection = "down", )

    level1[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf(), ladderDirection = "up")
    level1[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf(),)

    level1[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("east"), ladderDirection = "down")
    level1[0][1][1][1] = mazeRoom(number = "0.1.1", color = "blue", doors = listOf("west"),)
    //endregion

    //region level2 rooms
    level2[0][0][0][0] = mazeRoom(
            number = "0.0.0",
            color = "red",
            doors = listOf("east", ),
    )
    level2[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("west",),)
    level2[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf("east",),)
    level2[1][1][0][0] = mazeRoom(number = "1.1.0", color = "red", doors = listOf("west"),)

    level2[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("east"),)
    level2[0][1][1][0] = mazeRoom(number= "0.1.1", color = "red", doors = listOf("west"),)
    level2[1][0][1][0] = mazeRoom(number = "1.0.1", color = "red", doors = listOf(),)
    level2[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf(),)

    level2[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("north", "east"),)
    level2[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf("west", ),)
    level2[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south",),)
    level2[1][1][0][1] = mazeRoom(number = "1.1.0", color = "blue", doors = listOf(), ladderDirection = "up")

    level2[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("west"), ladderDirection = "down",)
    level2[0][1][1][1] = mazeRoom(
            number = "0.1.1",
            color = "blue",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 2! To go on to Level 3 say 'yes'."
    ) {
        action("yes") {
            currentLevel = level3
            boxes = listOf(Coordinates(x = 1, y = 1, z = 1, w = 1), Coordinates(0, 1, 1, 2), Coordinates(0,0,0,2))
            go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
        }
    }
    level2[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("north",),)
    level2[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf("south", "east"),)
    //endregion

    //region level3 rooms
    //region level3 red
    level3[0][0][0][0] = mazeRoom(number = "0.0.0", color = "red", doors = listOf(),)
    level3[0][1][0][0] = mazeRoom(number = "0.1.0", color = "red", doors = listOf("north"),)
    level3[1][0][0][0] = mazeRoom(number = "1.0.0", color = "red", doors = listOf(), ladderDirection = "up")
    level3[1][1][0][0] = mazeRoom(number = "1.1.0", color = "red", doors = listOf("south",),)


    level3[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf(),)
    level3[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf(),)
    level3[1][0][1][0] = mazeRoom(
            number = "1.0.1",
            color = "red",
            doors = listOf(),
            ladderDirection = "down",
            lock = "down",
            link = Coordinates(0,0, 1, 2)
    )
    level3[1][1][1][0] = mazeRoom(number = "1.1.1", color = "red", doors = listOf(),)
    //endregion

    //region level3 blue
    level3[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("east"), ladderDirection = "up")
    level3[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf("north", "west"),)
    level3[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf(),)
    level3[1][1][0][1] = mazeRoom(number = "1.1.0", color = "blue", doors = listOf("south"),)

    level3[0][0][1][1] = mazeRoom(
            number = "0.0.1",
            color = "blue",
            doors = listOf("north", "east"),
            ladderDirection = "down",
            lock = "north",
            link = Coordinates(0, 1,0,2),

    )
    level3[0][1][1][1] = mazeRoom(
            number = "0.1.1",
            color = "blue",
            doors = listOf("north", "west"),
            hasPlate = true,
            plateColor = "blue and green",
            lock = "west",
            link = Coordinates(x = 0, y = 1, z = 1, w = 1)
    )
    level3[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf("south"),)
    level3[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("south"),)
    //endregion

    //region level3 green
    level3[0][0][0][2] = mazeRoom(number = "0.0.0", color = "green", doors = listOf("east"),)
    level3[0][1][0][2] = mazeRoom(
            number = "0.1.0",
            color = "green",
            doors = listOf("west"),
            hasPlate = true,
            plateColor = "blue"
    )
    level3[1][0][0][2] = mazeRoom(
            number = "1.0.0",
            color = "green",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 3! That's all for now."
    )
    level3[1][1][0][2] = mazeRoom(number = "1.1.0", color = "green", doors = listOf(),)

    level3[0][0][1][2] = mazeRoom(
            number = "0.0.1",
            color = "green",
            doors = listOf("east"),
            hasPlate = true,
            plateColor = "red",
            lock = "east",
            link = Coordinates(0,1,1,1)
    )
    level3[0][1][1][2] = mazeRoom(
            number = "0.1.1",
            color = "green",
            doors = listOf("west", "north"),
            lock = "west",
            link = Coordinates(0, 1, 1, 1)
    )
    level3[1][0][1][2] = mazeRoom(number = "1.0.1", color = "green", doors = listOf(),)
    level3[1][1][1][2] = mazeRoom(number = "1.1.1", color = "green", doors = listOf("south"),)
    //endregion
    //endregion

    val tutorial = room() {
        onEnter { if (!hasStarted1) {
            say("Welcome to Shift! Please read these instructions carefully!")
            say("In this game you will be explore a series of four-dimensional mazes.")
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
                intro4 = true
            }
            else if (!intro5) {
                say("In addition to moving in the three dimensions we're familiar with via ladders and doors, " +
                        "you can also shift ana or kata, which are 4D directions.")
                say("This is a special power you can do ANYWHERE, ANYTIME.")
                say("Remember, ana and kata are directions along a FOURTH axis, so you when you shift ana or shift kata, " +
                        "you won't go up, down, north, south, east or west. You'll go ana, or kata.")
                say("They're two entirely different spacial directions.")
                say("Once you begin playing, you'll see how this works.")
                intro5 = true
            }
            else if (!intro6) {
                say("To shift ana or kata, just type the words, 'shift ana' or 'shift kata' and press enter.")
                say("Remember, that DOES NOT mean using the SHIFT key on your computer.")
                say("Just type in the word shift, all in lower case, followed by the word 'ana' or 'kata.'")
                say("If you get stuck, try re-reading these instructions.")
                say("TO BEGIN LEVEL 1, PRESS THE ENTER KEY NOW")
                intro6 = true
            }
            else if (!intro7) {
                say("WELCOME TO LEVEL 1!")
                say("HINT: You're looking for the room with 0.0.0 marked in red.")
                intro7 = true
                go(level1[player.x!!][player.y!!][player.z!!][player.w!!]!!)
            }
        }
        action("go to level two"){
            player.x = 0
            player.y = 0
            player.z = 0
            player.w = 0
            currentLevel = level2
            go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
        }
        action("go to level three") {
            player.x = 0
            player.y = 1
            player.z = 1
            player.w = 1
            currentLevel = level3
            boxes = listOf(Coordinates(1, 1, 1, 1), Coordinates(0, 1, 1, 2), Coordinates(0,0,0,2))
            go(currentLevel[player.x!!][player.y!!][player.z!!][player.w!!]!!)
        }
    }
    initialRoom = tutorial
}

fun main() {
    runGame(main)
}
