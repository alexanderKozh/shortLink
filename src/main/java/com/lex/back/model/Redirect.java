package com.lex.back.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "redirect")
public class Redirect implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "moment")
    private Date moment;

    public Redirect() {
    }

    public Redirect(Long linkId, Date moment) {
        this.linkId = linkId;
        this.moment = moment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Redirect redirect = (Redirect) o;
        return Objects.equals(id, redirect.id) &&
                Objects.equals(linkId, redirect.linkId) &&
                Objects.equals(moment, redirect.moment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, linkId, moment);
    }
}
