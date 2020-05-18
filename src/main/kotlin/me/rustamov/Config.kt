package me.rustamov

import org.reflections.Reflections
import kotlin.reflect.KClass

interface Config {
    val scanner: Reflections
    fun <T> getImplClass(ifc: Class<T>): Class<in T>
}