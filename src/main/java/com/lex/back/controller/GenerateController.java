package com.lex.back.controller;

import com.lex.back.model.Link;
import com.lex.back.reposiroty.LinkRepository;
import com.lex.back.util.ShortLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("generate")
public class GenerateController {

    private static final String SHORT_LINK_PREFIX = "/l/";

    @Autowired
    LinkRepository linkRepository;

    @PostMapping(produces = "application/json")
    public Map<String, String> generate(@RequestParam(value = "original") String original) {
        final Link link = linkRepository
                .getByOriginal(original)
                .orElseGet(() -> {
                    Link result = new Link();
                    result.setOriginal(original);
                    result.setSimple(ShortLinkUtil.generateShortLink());
                    linkRepository.save(result);
                    return result;
                });

        return new HashMap<String, String>() {{
            put("link", SHORT_LINK_PREFIX + link.getSimple());
        }};
    }
}
