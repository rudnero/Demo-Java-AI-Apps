package com.otus.java.advanced.domain;


import dev.langchain4j.model.output.structured.Description;

public class Student {
    @Description("Имя")
    public String name;
    @Description("Возраст")
    public String age;
    @Description("Город")
    public String city;

    @Override
    public String toString() {
        return this.name + ", " + this.age + " лет, " + this.city;
    }
}
