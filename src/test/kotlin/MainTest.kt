import dev.mythos.test.assertFollows
import dev.mythos.test.branch
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun testLevel1() {
        assertFollows(main, branch {
            player("go up")
            game(Regex(".*0\\.1\\.1.*red.*"))
            player("go north")
            game("There's no door that way.")
            player("go south")
            game("There's no door that way.")
            player("go up")
            game("There's no ladder that way.")
            player("shift ana")
            game("There's nothing in that direction.")
            player("go west")
            game(Regex(".*0\\.0\\.1.*red.*"))
            player("shift kata")
            game(Regex(".*0\\.0\\.1.*blue.*"))
            player("go down")
            game(Regex(".*0\\.0\\.0.*blue.*"))
            player("shift ana")
            game(Regex(".*yes.*"))
            player("yes")
            game(Regex(".*0\\.0\\.0.*red.*"))
        })
    }
}