package com.lex.back.controller;

import com.lex.back.model.entity.Link;
import com.lex.back.model.dto.OriginalLinkDTO;
import com.lex.back.model.dto.ShortLinkDTO;
import com.lex.back.reposiroty.LinkRepository;
import com.lex.back.util.ShortLinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ShortLinkDTO generate(@RequestBody OriginalLinkDTO originalLinkDTO) {
        String original = originalLinkDTO.getOriginal();

        final Link link = linkRepository
                .getByOriginal(original)
                .orElseGet(() -> {
                    Link result = new Link();
                    result.setOriginal(original);
                    result.setSimple(ShortLinkUtil.generateShortLink());
                    linkRepository.save(result);
                    return result;
                });

        return new ShortLinkDTO(SHORT_LINK_PREFIX + link.getSimple());
    }
}
