package org.example.config;

import lombok.SneakyThrows;
import org.example.interfaces.Config;
import org.example.interfaces.Policeman;
import org.example.model.AngryPoliceman;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    private static ObjectFactory instance = new ObjectFactory();
    private Config config;

    private ObjectFactory(){
        config = new JavaConfig(
                new Reflections("org.example"),
                new HashMap<>(Map.of(Policeman.class, AngryPoliceman.class)));

    }

    public static ObjectFactory getInstance(){
        return instance;
    }

    @SneakyThrows
    public <T> T creatObject(Class<T> classType){
        if(classType.isInterface()){
            classType =  (Class<T>) config.getImplClass(classType);
        }
        return classType.getDeclaredConstructor().newInstance();
    }

}
