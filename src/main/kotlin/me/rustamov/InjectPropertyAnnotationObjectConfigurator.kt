package me.rustamov

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors.toMap

class InjectPropertyAnnotationObjectConfigurator : ObjectConfigurator {
    private var propertiesMap: Map<String, String>

    init {
        val propsStream = this::class.java.getResourceAsStream("/application.properties") // if its inside a jar
        val linesStream = BufferedReader(InputStreamReader(propsStream)).lines()
        propertiesMap = linesStream.map { line -> line.split("=") }.collect(toMap({ it[0] }, { it[1] }))
    }

    override fun configure(obj: Any, context: ApplicationContext) {
        val jClass = obj.javaClass
        jClass.declaredFields
            .forEach { field ->
                val annotationField = jClass.declaredMethods
                    .firstOrNull { it.name == "${field.name}\$annotations" }
                    ?.getAnnotation(InjectProperty::class.java)
                    ?: return
                val value = propertiesMap[(annotationField as InjectProperty).value.takeIf { it != "" } ?: field.name]
                field.trySetAccessible()
                field.set(obj, value)
            }
    }
}