package com.lex.back.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Link link = (Link) o;
        return Objects.equals(id, link.id) &&
                Objects.equals(getOriginal(), link.getOriginal()) &&
                Objects.equals(getSimple(), link.getSimple());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getOriginal(), getSimple());
    }
}
