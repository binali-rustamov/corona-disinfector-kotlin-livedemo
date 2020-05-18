package me.rustamov

@Deprecated(message = "Хватит использовать этот класс!", level = DeprecationLevel.WARNING)
@Singleton
class SimpleRecommendator : Recommendator {
    @InjectProperty("wisky")
    private lateinit var alcohol: String

    init {
        println("recommendator was created")
    }

    override fun recommend() {
        println("to protect from covid-2019, drink $alcohol")
    }
}