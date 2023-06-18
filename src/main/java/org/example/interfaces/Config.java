package org.example.interfaces;

public interface Config {
   <T> Class<? extends T> getImplClass(Class<T> type);
}
