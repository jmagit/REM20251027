package com.example.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@Scope("prototype")
public class PrototypeBean {

    private static int instances = 0;
    private final int id;
    private int counter = 0;

    public PrototypeBean() {
        id = ++instances;
    }

    @PostConstruct
    public void init() {
        System.out.println("PrototypeBean init, id=" + id);
    }

    public int getNext() { 
    	return ++counter; 
    }
    
    @Override
    public String toString() {
        return "PrototypeBean#" + id + " counter: " + counter;
    }
}
