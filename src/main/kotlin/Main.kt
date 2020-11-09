import dev.mythos.dsl.Room
import dev.mythos.dsl.game
import dev.mythos.dsl.room
import dev.mythos.game.runGame
import dev.mythos.dsl.Room
import dev.mythos.dsl.room


val level1 =
        Array(10) {
            Array(5) {
                Array(9) {
                    Array<Room?>(7) {
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

fun makeRoom(): Room {
    return room {
        action("go (.*)") { (direction) ->
            when (direction) {
                "north" -> {
                    if (currentLevel[x][y+1][z][w] != null) {
                        y = y + 1
                        go(currentLevel[x][y][z][w]!!)
                    }
                }
            }
        }
    }
}

level1[0][0][0][0] = room {}
level1[0][0][0][1] = makeRoom()


val main = game {
    introduction = "Welcome to Mythos!"

    action("say hello") {
        say("Thanks for joining us!")
    }
}

fun main() {
    runGame(main)
}