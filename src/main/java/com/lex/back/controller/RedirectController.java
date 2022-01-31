package com.lex.back.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lex.back.model.entity.Link;
import com.lex.back.model.entity.Redirect;
import com.lex.back.reposiroty.LinkRepository;
import com.lex.back.reposiroty.RedirectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("l")
public class RedirectController {

    private final Cache<String, Link> cache = CacheBuilder.newBuilder().weakKeys().build();

    private final LinkRepository linkRepository;
    private final RedirectRepository redirectRepository;

    @Autowired
    public RedirectController(LinkRepository linkRepository, RedirectRepository redirectRepository) {
        this.linkRepository = linkRepository;
        this.redirectRepository = redirectRepository;
    }

    @GetMapping("/{some-short-name}")
    public RedirectView redirectWithUsingRedirectView(
            @PathVariable(name = "some-short-name") String shortPath
    ) throws ExecutionException {
        final Link link = cache.get(
                shortPath,
                () -> linkRepository
                        .getBySimple(shortPath)
                        .orElseThrow(() -> new IllegalStateException(
                                String.format("Link by shortName %s is not found", shortPath))
                        )
        );

        Redirect redirect = new Redirect();
        redirect.setLinkId(link.getId());
        redirect.setMoment(new Date());
        redirectRepository.save(redirect);

        return new RedirectView(link.getOriginal());
    }
}
