package com.lex.back.model.dto;

public class ShortLinkDTO {

    private String link;

    public ShortLinkDTO() {
    }

    public ShortLinkDTO(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
