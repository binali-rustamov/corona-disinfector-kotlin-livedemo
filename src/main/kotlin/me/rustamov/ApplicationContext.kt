package me.rustamov

class ApplicationContext(val config: Config) {
    var factory: ObjectFactory? = null
        set
    private val cache: MutableMap<Class<*>, Any> = hashMapOf()
    fun <T : Any> getObject(type: Class<T>): T {
        if (factory == null) throw RuntimeException("Please provide ObjectFactory instance")
        if (cache.containsKey(type)) return cache[type] as T
        var implClass: Class<in T> = type
        if (type.isInterface) {
            implClass = config.getImplClass(type)
        }
        val obj = factory!!.createObject(implClass)
        if (implClass.isAnnotationPresent(Singleton::class.java)) cache[type] = obj
        return obj
    }
}
