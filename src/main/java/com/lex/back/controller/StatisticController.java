package com.lex.back.controller;

import com.lex.back.model.StatisticDTO;
import com.lex.back.reposiroty.RedirectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("stats")
public class StatisticController {

    private final RedirectRepository redirectRepository;

    @Autowired
    public StatisticController(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    @GetMapping(value = "/{some-short-name}")
    public StatisticDTO getStatistic(
            @PathVariable(name = "some-short-name") String shortName
    ) {
        return redirectRepository.getStatisticByShortName(shortName)
                .orElseThrow(() -> new IllegalStateException("Statistics not found"));
    }

    @GetMapping
    public List<StatisticDTO> getStatistics(
            @RequestParam(value = "page", defaultValue = "1") @Min(1) Integer page,
            @RequestParam(value = "count", defaultValue = "10") @Min(1) @Max(100) Integer count
    ) {
        return redirectRepository.getStatistics(PageRequest.of(--page, count));
    }
}
