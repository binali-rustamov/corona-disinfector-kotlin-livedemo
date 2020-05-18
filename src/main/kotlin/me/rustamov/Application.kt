package me.rustamov

import kotlin.reflect.KClass

class Application {
    companion object {
        fun run(packageToScan: String, interfaceImplMapping: MutableMap<Class<*>, Class<*>>): ApplicationContext {
            val config = JavaConfig(packageToScan, interfaceImplMapping)
            val context = ApplicationContext(config)
            val objectFactory = ObjectFactory(context)
            context.factory = objectFactory
            return context
        }
    }
}