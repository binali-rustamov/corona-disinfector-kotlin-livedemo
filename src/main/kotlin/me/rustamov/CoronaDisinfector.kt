package me.rustamov

class CoronaDisinfector {
    @InjectByType
    private lateinit var policeman: Policeman

    @InjectByType
    private lateinit var announcer: Announcer

    fun start(room: Room) {
        announcer.announce("Начинаем дезинфекцию, всё вон!")
        policeman.makePeopleLeaveRoom()
        disinfect(room)
        announcer.announce("Рискните зайти обратно")
    }

    private fun disinfect(room: Room) {
        println("зачитывается молитва: 'коронавирус кет кет кет!' - молитва прочитана, вирус низвергнут в ад")
    }
}