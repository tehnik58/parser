package com.example.demo.services;
import  java.util.*;
public interface IService<T> {
      List<T> getEntities();
      T getEntityById(long id);
}
