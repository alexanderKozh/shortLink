package com.lex.back.util;

import java.util.UUID;

public final class ShortLinkUtil {

    private ShortLinkUtil() {
    }

    //TODO реализовать механизм генерации человечитаемой ссылки, сохранить уникальность (инкремент?)
    public static String generateShortLink() {
        return UUID.randomUUID().toString();
    }
}
