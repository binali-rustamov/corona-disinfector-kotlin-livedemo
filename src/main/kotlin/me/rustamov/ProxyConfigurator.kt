package me.rustamov

interface ProxyConfigurator {
    fun replaceWithProxyIfNeeded(obj: Any, implClass: Class<*>): Any
}