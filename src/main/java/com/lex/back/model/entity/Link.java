package com.lex.back.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "link")
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "original")
    private String original;

    @Column(name = "simple")
    private String simple;

    public Link() {
    }

    public Link(String path, String simple) {
        this.original = path;
        this.simple = simple;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String path) {
        this.original = path;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String shortPath) {
        this.simple = shortPath;
    }
}
