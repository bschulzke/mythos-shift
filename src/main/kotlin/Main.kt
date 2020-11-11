import dev.mythos.dsl.Room
import dev.mythos.dsl.game
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

var hasStarted = false


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
            other = "HINT: You're looking for the room with 0.0.0 marked in red.",
    )

    level1[0][0][1][0] = mazeRoom(number = "0.0.1", color = "red", doors = listOf("east"), ladderDirection = "")
    level1[0][1][1][0] = mazeRoom(number = "0.1.1", color = "red", doors = listOf("west"), ladderDirection = "down")

    level1[0][0][0][1] = mazeRoom(number = "0.0.0", color = "blue", doors = listOf("east"), ladderDirection = "up")
    level1[0][1][0][1] = mazeRoom(number = "0.1.0", color = "blue", doors = listOf(), ladderDirection = "")

    level1[0][0][1][1] = mazeRoom(number = "0.0.1", color = "blue", doors = listOf("east"), ladderDirection = "down")
    level1[0][1][1][1] = mazeRoom(number = "0.1.1", color = "blue", doors = listOf("west"), ladderDirection = "down")
    //endregion

    //region level2 rooms
    level2[0][0][0][0] = mazeRoom(
            number = "0.0.0",
            color = "red",
            doors = listOf("north", "east", ),
            ladderDirection = "up",
            other = "Hint: The final room is marked with [TBD]."
    )
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
