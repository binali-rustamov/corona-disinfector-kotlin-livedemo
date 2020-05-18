package me.rustamov

import me.rustamov.ApplicationContext

interface ObjectConfigurator {
    fun configure(obj: Any, context: ApplicationContext);
}