package org.example.config;

import lombok.SneakyThrows;
import org.example.annotation.InjectByType;
import org.example.interfaces.ObjectConfigurator;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(InjectByType.class)){
                Object object = ObjectFactory.getInstance().creatObject(field.getType());
                field.setAccessible(true);
                field.set(t, object);
            }
        }
    }
}
