import dev.mythos.dsl.game
import dev.mythos.game.runGame

val main = game {
    introduction = "Welcome to Mythos!"

    action("say hello") {
        say("Thanks for joining us!")
    }
}

fun main() {
    runGame(main)
}