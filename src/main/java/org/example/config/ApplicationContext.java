package org.example.config;

import lombok.Getter;
import org.example.annotation.Singleton;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private Map<Class, Object> cache = new HashMap<>();
    @Getter
    private Reflections scanner;
    private JavaConfig config;
    private ObjectFactory factory;

    public ApplicationContext(String packagesToScan, Map<Class, Class> ifc2Impl) {
      scanner = new Reflections(packagesToScan);
      config = new JavaConfig(scanner, ifc2Impl);
      factory = new ObjectFactory(this);

    }

    public <T> T getObject(Class<T> type){
        if(cache.containsKey(type)){
            return (T) cache.get(type);
        }
        Class<T> implClass = resolveImpl(type);
        T createdObject = factory.creatObject(implClass);
        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, createdObject);
        }
        return createdObject;
    }

    private <T> Class<T> resolveImpl(Class<T> classType) {
        if(classType.isInterface()){
            classType =  (Class<T>) config.getImplClass(classType);
        }
        return classType;
    }
}
