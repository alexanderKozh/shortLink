package com.lex.back.model.dto;

import java.io.Serializable;

public interface StatisticDTO extends Serializable {
    String getLink();

    String getOriginal();

    Long getRank();

    Long getCount();
}
