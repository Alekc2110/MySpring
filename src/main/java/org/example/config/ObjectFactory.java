package org.example.config;

import lombok.SneakyThrows;
import org.example.annotation.InjectProperty;
import org.example.interfaces.Config;
import org.example.interfaces.ObjectConfigurator;
import org.example.interfaces.Policeman;
import org.example.model.AngryPoliceman;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectFactory {

    private static ObjectFactory instance = new ObjectFactory();
    private List<ObjectConfigurator> configuratorList = new ArrayList<>();
    private Config config;

    @SneakyThrows
    private ObjectFactory(){
        Reflections scanner = new Reflections("org.example");
        config = new JavaConfig(
                scanner,
                new HashMap<>(Map.of(Policeman.class, AngryPoliceman.class)));

        Set<Class<? extends ObjectConfigurator>> configuratorImplClasses = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : configuratorImplClasses) {
            configuratorList.add(aClass.getDeclaredConstructor().newInstance());
        }

    }

    public static ObjectFactory getInstance(){
        return instance;
    }

    @SneakyThrows
    public <T> T creatObject(Class<T> classType){
        if(classType.isInterface()){
            classType =  (Class<T>) config.getImplClass(classType);
        }
        T imlObjectInstance = classType.getDeclaredConstructor().newInstance();

        configureObject(imlObjectInstance);

        return imlObjectInstance;
    }

    private <T> void configureObject(T imlObjectInstance) {
        configuratorList.forEach(configurator-> configurator.configure(imlObjectInstance));
    }

}
