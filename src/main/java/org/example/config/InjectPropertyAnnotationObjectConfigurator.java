package org.example.config;

import lombok.SneakyThrows;
import org.example.annotation.InjectProperty;
import org.example.interfaces.ObjectConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private final Map<String, String> map;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("application.properties")).getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        map = lines.map(line -> {
            String[] split = line.split("=");
            return Arrays.stream(split).map(String::trim).toArray();
        }).collect(Collectors.toMap(arr -> (String)arr[0], arr -> (String)arr[1]));

    }

    @Override
    @SneakyThrows
    public void configure(Object t) {
        Class<?> classType = t.getClass();

        for (Field field : classType.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null){
                String propertyName = annotation.value();
                String propertyValue = map.get(propertyName);
                field.setAccessible(true);
                field.set(t, propertyValue);
            }
        }
    }
}
