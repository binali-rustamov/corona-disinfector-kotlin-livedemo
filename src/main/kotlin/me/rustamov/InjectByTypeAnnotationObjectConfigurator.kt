package me.rustamov

class InjectByTypeAnnotationObjectConfigurator : ObjectConfigurator {

    override fun configure(obj: Any, context: ApplicationContext) {
        val jClass = obj.javaClass
        jClass.declaredFields
            .asSequence()
            .filter {
                jClass.declaredMethods.any { m ->
                    m.name == "${it.name}\$annotations" && m.isAnnotationPresent(InjectByType::class.java)
                }
            }
            .forEach {
                it.trySetAccessible()
                val objToSet = context.getObject(it.type)
                it.set(obj, objToSet)
            }
    }
}