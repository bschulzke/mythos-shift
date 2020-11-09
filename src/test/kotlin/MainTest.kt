import dev.mythos.test.assertFollows
import dev.mythos.test.branch
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun testTheGame() {
        assertFollows(main, branch {
            game("Welcome to Mythos!")
            player("say hello")
            game("Thanks for joining us!")
        })
    }
}