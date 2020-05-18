package me.rustamov

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val context = Application.run(
                "me.rustamov",
                hashMapOf(Policeman::class.java to FriendlyPoliceman::class.java)
            )
            val disinfector = context.getObject(CoronaDisinfector::class.java)
            disinfector.start(Room("Office"))
        }
    }
}
