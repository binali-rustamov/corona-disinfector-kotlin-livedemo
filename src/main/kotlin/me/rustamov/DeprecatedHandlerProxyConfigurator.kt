package me.rustamov

import net.sf.cglib.proxy.Enhancer
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class DeprecatedHandlerProxyConfigurator : ProxyConfigurator {
    override fun replaceWithProxyIfNeeded(obj: Any, implClass: Class<*>): Any {
        val annotation = implClass.getAnnotation(Deprecated::class.java) ?: return obj

        if (implClass.interfaces.any()) return Proxy.newProxyInstance(implClass.classLoader, implClass.interfaces)
        { _, method, args -> getInvocationHandlerLogic(method, args, obj, annotation, implClass) }

        return Enhancer.create(implClass, object : net.sf.cglib.proxy.InvocationHandler {
            override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any? =
                getInvocationHandlerLogic(method, args, obj, annotation, implClass)
        })
    }

    private fun getInvocationHandlerLogic(
        method: Method,
        args: Array<out Any>?,
        obj: Any,
        annotation: Deprecated,
        implClass: Class<*>
    ): Any? {
        println("${annotation.message} : $implClass")
        return method.invoke(obj)
    }
}