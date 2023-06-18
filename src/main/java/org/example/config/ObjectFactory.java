package org.example.config;

import lombok.SneakyThrows;
import org.example.interfaces.Config;
import org.reflections.Reflections;

public class ObjectFactory {

    private static ObjectFactory instance = new ObjectFactory();
    private Config config;

    private ObjectFactory(){
        config = new JavaConfig(new Reflections("org.example"));

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
