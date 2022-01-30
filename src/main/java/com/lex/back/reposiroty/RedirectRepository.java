package com.lex.back.reposiroty;

import com.lex.back.model.Redirect;
import com.lex.back.model.StatisticDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RedirectRepository extends JpaRepository<Redirect, Long> {

    String STATISTICS_QUERY = "SELECT * FROM ( " +
            "SELECT " +
                "l.simple AS link, " +
                "l.original, " +
                "RANK() OVER(ORDER BY COUNT(*) DESC) AS rank, " +
                "COUNT(*) as count " +
            "FROM redirect r " +
            "JOIN link l ON l.id = r.link_id " +
            "GROUP BY simple, original " +
            ") t ";

    @Query(nativeQuery = true, value = STATISTICS_QUERY + "WHERE link = :simple")
    Optional<StatisticDTO> getStatisticByShortName(@Param("simple") String simple);

    @Query(nativeQuery = true, value = STATISTICS_QUERY + " ORDER BY rank DESC")
    List<StatisticDTO> getStatistics(Pageable pageable);
}
