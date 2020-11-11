import dev.mythos.test.assertFollows
import dev.mythos.test.branch
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun testTheGame() {
        assertFollows(main, branch {
            player("go up")
            player("go west")
            player("shift kata")
            player("go down")
            player("shift ana")
            player("yes")
            game(Regex(".*0\\.0\\.0.*"))
        })
    }
}