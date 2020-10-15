package com.group9.prevue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.group9.prevue.model.EGenre;
import com.group9.prevue.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

	Optional<Genre> findByGenre(EGenre genre);
}
