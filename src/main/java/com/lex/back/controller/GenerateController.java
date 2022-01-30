package com.lex.back.controller;

import com.lex.back.model.Link;
import com.lex.back.reposiroty.LinkRepository;
import com.lex.back.util.ShortLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("generate")
public class GenerateController {

    public static final String SHORT_LINK_PREFIX = "/l/";

    private final LinkRepository linkRepository;

    @Autowired
    public GenerateController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @PostMapping
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
