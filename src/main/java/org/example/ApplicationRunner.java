package org.example;

import org.example.config.ApplicationContext;

import java.util.Map;

public class ApplicationRunner {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2Impl){
       return new ApplicationContext(packageToScan, ifc2Impl);
    }
}
