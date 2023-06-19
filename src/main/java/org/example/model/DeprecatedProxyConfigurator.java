package org.example.model;

import org.example.interfaces.ProxyConfigurator;

import java.lang.reflect.Proxy;

public class DeprecatedProxyConfigurator implements ProxyConfigurator {
    @Override
    public <T> T createProxy(Class<T> type, T object) {
        if (type.isAnnotationPresent(Deprecated.class)) {
            return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("что же ты урод пользуешься Deprecated классами " + object.getClass());
                        return method.invoke(object, args);
                    });
        }
        return object;
    }
}
