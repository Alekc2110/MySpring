package org.example.config;

import lombok.SneakyThrows;
import org.example.interfaces.ObjectConfigurator;

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

        return imlObjectInstance;
    }



    private <T> void configureObject(T imlObjectInstance) {
        configuratorList.forEach(configurator-> configurator.configure(imlObjectInstance, context));
    }

}
