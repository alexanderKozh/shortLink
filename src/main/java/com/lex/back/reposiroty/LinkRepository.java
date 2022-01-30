package com.lex.back.reposiroty;

import com.lex.back.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> getByOriginal(String original);
    Optional<Link> getBySimple(String simple);
}
