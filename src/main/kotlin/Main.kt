import dev.mythos.dsl.Room
import dev.mythos.dsl.game
import dev.mythos.game.runGame

data class Coordinates(
        var x: Int,
        val y: Int,
        val z: Int,
        val w: Int
)

var playerPosition = Coordinates (x = 0, y = 1, z = 0, w = 0)

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

var a = 0
var b = 1
var c = 1
var d = 1

var currentLevel = level1

var hasStarted1 = false
var hasStarted2 = false


val main = game {

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
    level1[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf("west"), ladderDirection = "down")

    level1[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("east"), ladderDirection = "up")
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

    level2[0][0][0][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("north", "east"),)
    level2[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf("west", ),)
    level2[1][0][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf("south",),)
    level2[1][1][0][1] = mazeRoom(number = "1.0.0", color = "blue", doors = listOf(), ladderDirection = "up")

    level2[1][1][1][1] = mazeRoom(number = "1.1.1", color = "blue", doors = listOf("west"), ladderDirection = "down",)
    level2[0][1][1][1] = mazeRoom(
            number = "0.1.1",
            color = "blue",
            doors = listOf(),
            isFinish = true,
            other = "You finished Level 2!"
    )
    level2[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("north",),)
    level2[1][0][1][1] = mazeRoom(number = "1.0.1", color = "blue", doors = listOf("south", "east"),)
    //endregion

    initialRoom = level1[0][1][0][0]!!
}

fun main() {
    runGame(main)
}
