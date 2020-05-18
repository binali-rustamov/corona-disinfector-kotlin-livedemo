package me.rustamov

class ConsoleAnnouncer : Announcer {
    @InjectByType
    private lateinit var recommendator: Recommendator
    override fun announce(message: String) {
        println(message)
        recommendator.recommend()
    }
}