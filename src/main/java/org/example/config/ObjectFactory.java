package org.example.config;

import lombok.SneakyThrows;
import org.example.interfaces.ObjectConfigurator;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {

    private List<ObjectConfigurator> configuratorList = new ArrayList<>();
    private ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context){
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> configuratorImplClasses = context.getScanner()
                                                                                  .getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : configuratorImplClasses) {
            configuratorList.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T creatObject(Class<T> classType){

        T imlObjectInstance = classType.getDeclaredConstructor().newInstance();

        configureObject(imlObjectInstance);

        invokeInitMethod(classType, imlObjectInstance);

        if (classType.isAnnotationPresent(Deprecated.class)) {
           return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), classType.getInterfaces(),
                   (proxy, method, args) -> {
               System.out.println("что же ты урод пользуешься Deprecated классами " + imlObjectInstance.getClass());
              return method.invoke(imlObjectInstance, args);
           });
        }

        return imlObjectInstance;
    }

    private <T> void invokeInitMethod(Class<T> classType, T imlObjectInstance) throws IllegalAccessException, InvocationTargetException {
        for (Method method : classType.getMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)){
                method.invoke(imlObjectInstance);
            }
        }
    }


    private <T> void configureObject(T imlObjectInstance) {
        configuratorList.forEach(configurator-> configurator.configure(imlObjectInstance, context));
    }

}
