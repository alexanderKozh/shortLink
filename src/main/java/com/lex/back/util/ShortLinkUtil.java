package com.lex.back.util;

import java.util.UUID;

public final class ShortLinkUtil {

    private ShortLinkUtil() {
    }

    public static String generateShortLink() {
        return UUID.randomUUID().toString();
    }
}
