import dev.mythos.test.assertFollows
import dev.mythos.test.branch
import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun testLevel1() {
        assertFollows(main, branch {
            player("")
            player("")
            player("")
            player("")
            player("")
            player("")
            player("")
            player("show compass")
            game("""
                       ▲
                       N
                   ◄W    E►
                       S
                       ▼
                       
                    """.trimIndent())
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
    @Test
    fun testLevel2() {
        assertFollows(main, branch {
            player("go to level two")
            game(Regex(".*0\\.0\\.0.*red.*"))
            player("go east")
            game(Regex(".*0\\.1\\.0.*red.*"))
            player("go south")
            game("There's no door that way.")
            player("go north")
            game("There's no door that way.")
            player("shift kata")
            game(Regex(".*0\\.1\\.0.*blue.*"))
            player("go west")
            game(Regex(".*0\\.0\\.0.*blue.*"))
            player("go north")
            game(Regex(".*1\\.0\\.0.*blue.*"))
            player("shift ana")
            game(Regex(".*1\\.0\\.0.*red.*"))
            player("go east")
            game(Regex(".*1\\.1\\.0.*red.*"))
            player("shift kata")
            game(Regex(".*1\\.1\\.0.*blue.*"))
            player("go up")
            game(Regex(".*1\\.1\\.1.*blue.*"))
            player("go west")
            game(Regex(".*1\\.0\\.1.*blue.*"))
            player("go south")
            game(Regex(".*0\\.0\\.1.*blue.*"))
            player("shift ana")
            game(Regex(".*0\\.0\\.1.*red.*"))
            player("go east")
            game(Regex(".*0\\.1\\.1.*red.*"))
            player("shift kata")
            game(Regex(".*You finished Level 2!.*"))
        })
    }
    @Test
    fun testLevel3() {
        assertFollows(main, branch {
            player("go to level three")
            game(Regex(".*0\\.1\\.1.*blue.*"))
            player("go north")
            game(Regex(".*1\\.1\\.1.*blue.*"))
            player("push box south")
            game(Regex(".*0\\.1\\.1.*blue.*"))
        })
    }
}