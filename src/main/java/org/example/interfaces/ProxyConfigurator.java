package org.example.interfaces;

public interface ProxyConfigurator {
  <T> T createProxy(Class<T> type, T object);
}
