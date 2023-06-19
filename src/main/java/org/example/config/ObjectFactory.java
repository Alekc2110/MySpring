package org.example.config;

import lombok.SneakyThrows;
import org.example.interfaces.ObjectConfigurator;
import org.example.interfaces.ProxyConfigurator;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {

    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
    private ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context){
        this.context = context;
        populateObjectConfiguratorList(context);
        populateProxyConfiguratorList(context);
    }

    @SneakyThrows
    private void populateObjectConfiguratorList(ApplicationContext context) {
        Set<Class<? extends ObjectConfigurator>> configuratorImplClasses = context.getScanner()
                                                                                  .getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : configuratorImplClasses) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    private void populateProxyConfiguratorList(ApplicationContext context) {
        Set<Class<? extends ProxyConfigurator>> configuratorImplClasses = context.getScanner()
                .getSubTypesOf(ProxyConfigurator.class);
        for (Class<? extends ProxyConfigurator> aClass : configuratorImplClasses) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T creatObject(Class<T> classType){

        T imlObjectInstance = classType.getDeclaredConstructor().newInstance();

        configureObject(imlObjectInstance);

        invokeInitMethod(classType, imlObjectInstance);

        return configureProxyObject(classType, imlObjectInstance);

    }

    private <T> void invokeInitMethod(Class<T> classType, T imlObjectInstance) throws IllegalAccessException, InvocationTargetException {
        for (Method method : classType.getMethods()) {
            if(method.isAnnotationPresent(PostConstruct.class)){
                method.invoke(imlObjectInstance);
            }
        }
    }


    private <T> void configureObject(T imlObjectInstance) {
        configurators.forEach(configurator-> configurator.configure(imlObjectInstance, context));
    }

    private <T> T configureProxyObject(Class<T> classType, T imlObjectInstance) {
        for (ProxyConfigurator configurator : proxyConfigurators) {
            T proxy = configurator.createProxy(classType, imlObjectInstance);
            if(proxy != imlObjectInstance) return proxy;
        }
        return imlObjectInstance;
    }

}
