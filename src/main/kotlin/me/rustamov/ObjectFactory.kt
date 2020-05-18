package me.rustamov

import javax.annotation.PostConstruct

class ObjectFactory(private val context: ApplicationContext) {
    private val configurators: List<ObjectConfigurator>
    private val proxyConfigurators: List<ProxyConfigurator>

    init {
        configurators = context.config.scanner.getSubTypesOf(ObjectConfigurator::class.java)
            .map { it.getDeclaredConstructor().newInstance() }
        proxyConfigurators = context.config.scanner.getSubTypesOf(ProxyConfigurator::class.java)
            .map { it.getDeclaredConstructor().newInstance() }
    }

    fun <T : Any> createObject(implClass: Class<in T>): T {
        val obj: T = create(implClass)
        configure(obj)
        invokeInit(implClass, obj)
        return wrapWithProxyIfNeeded(implClass, obj)
    }

    private fun <T : Any> wrapWithProxyIfNeeded(implClass: Class<in T>, obj: Any): T {
        var internalObj = obj
        proxyConfigurators.forEach { internalObj = it.replaceWithProxyIfNeeded(obj, implClass) }
        return internalObj as T
    }

    private fun <T : Any> invokeInit(implClass: Class<in T>, obj: T) =
        implClass.declaredMethods.filter { it.isAnnotationPresent(PostConstruct::class.java) }
            .forEach { it.invoke(obj) }

    private fun <T : Any> configure(obj: T) = configurators.forEach { it.configure(obj, context) }

    private fun <T : Any> create(implClass: Class<in T>): T = implClass.getDeclaredConstructor().newInstance() as T
}