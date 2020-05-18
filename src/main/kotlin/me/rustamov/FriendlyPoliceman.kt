package me.rustamov

import javax.annotation.PostConstruct

class FriendlyPoliceman : Policeman {
    @InjectByType
    private lateinit var recommendator: Recommendator
    private lateinit var text: String

    @PostConstruct
    fun init() {
        println(recommendator.javaClass)
        text = "пиф паф, бах бах, кыш, кыш!"
    }

    override fun makePeopleLeaveRoom() {
        println(text)
    }
}