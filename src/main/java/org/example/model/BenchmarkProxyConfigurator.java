package org.example.model;

import org.example.annotation.Benchmark;
import org.example.interfaces.ProxyConfigurator;

import java.lang.reflect.Proxy;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BenchmarkProxyConfigurator  implements ProxyConfigurator{

    public <T> T createProxy(Class<T> type, T object) {
        if (type.isAnnotationPresent(Benchmark.class)) {
            return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.println("Object "+ object.getClass() + " starts working at: "
                                + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
                        return method.invoke(object, args);

                    });
        }
        return object;
    }
}
