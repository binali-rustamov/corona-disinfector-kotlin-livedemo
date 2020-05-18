package me.rustamov

import org.reflections.Reflections
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass

class JavaConfig(
    packageToScan: String,
    private var interfaceToClassMappings: MutableMap<Class<*>, Class<*>>
) : Config {
    override val scanner: Reflections

    override fun <T> getImplClass(ifc: Class<T>): Class<in T> = interfaceToClassMappings
        .getOrPut(ifc) {
            val classes = scanner.getSubTypesOf(ifc)
            if (classes.size != 1) throw RuntimeException("$ifc has 0 or more than one impl please update your config")
            classes.first()
        } as Class<in T>

    init {
        scanner = Reflections(packageToScan)
    }

}