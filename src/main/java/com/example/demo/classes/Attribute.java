package com.example.demo.classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute {
    private String name;
    private String type;
    private String label;
    private String value;

    public Attribute(String name, String type, String label) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.value = null;
    }

    public Attribute(String name, String type, String label, String value) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.value = value;
    }
}

